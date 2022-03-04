package com.example.news.service;

import com.example.news.dto.UserDTO;
import com.example.news.entity.UserEntity;
import com.example.news.mapper.UserMapper;
import com.example.news.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    ObjectMapper mapper = new ObjectMapper();
    public void insertUser(UserDTO user) {
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePassword);
        UserEntity userEntity = UserMapper.INSTANCE.userDTOToEntity(user);
        userRepository.save(userEntity);
    }

    public boolean checkUserId(String userId) {
        if (userRepository.findByUserId(userId).isEmpty()) {
            return false;
        }
        return true;
    }


    @SneakyThrows
    private String getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "7d70450b31867bf622687771a20f2ad3");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/login");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequset = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequset,
                String.class
        );

        JsonNode returnNode = null;


        returnNode = mapper.readTree(responseEntity.getBody());

        return returnNode.get("access_token").asText();
    }

    @SneakyThrows
    private String getUserInfo(String accessToken) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequset = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequset,
                String.class
        );


        return responseEntity.getBody();
    }

    @SneakyThrows
    public String kakaoLogin(String code) {


        String userInfo = getUserInfo(getAccessToken(code));


        JsonNode jsonNode = mapper.readTree(userInfo);

        String username= "kakao_"+jsonNode.get("id").asText();

        Optional<UserEntity> userEntity = userRepository.findByUserId(username);

        if (userEntity.isEmpty()) {
            insertUser(UserDTO.builder().userId(username).userPassword(username).build());
        }

        Authentication kakaoUsernamePassword =
                new UsernamePasswordAuthenticationToken(username, username);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        return null;
    }
}
