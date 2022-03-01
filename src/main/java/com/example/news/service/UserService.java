package com.example.news.service;

import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    public final UserRepository userRepository;


}
