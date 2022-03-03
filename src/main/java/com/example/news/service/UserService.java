package com.example.news.service;

import com.example.news.dto.UserDTO;
import com.example.news.entity.UserEntity;
import com.example.news.mapper.UserMapper;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;

    public void insertUser(UserDTO user) {
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePassword);
        UserEntity userEntity = UserMapper.INSTANCE.userDTOToEntity(user);
        userRepository.save(userEntity);
    }

    public boolean checkUserId(String userId) {
        if(userRepository.findByUserId(userId).isEmpty()){
            return false;
        }
        return true;
    }

}
