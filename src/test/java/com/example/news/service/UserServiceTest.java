package com.example.news.service;

import com.example.news.dto.UserDTO;
import com.example.news.entity.UserEntity;
import com.example.news.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    public UserService userService;
    @Autowired
    public UserRepository userRepository;

    @Test
    public void insertUser() {

        UserDTO user = UserDTO.builder()
                .userId("2")
                .userPassword("1")
                .build();
        userService.insertUser(user);
        Assert.assertNotNull(userRepository.findByUserId("2").get());


    }
}
