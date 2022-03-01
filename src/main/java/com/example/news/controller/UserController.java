package com.example.news.controller;

import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class UserController {
    public final UserRepository userRepository;
    public final BCryptPasswordEncoder passwordEncoder;
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/login")
    public ModelAndView loginFrom() {

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView singUpFrom() {

        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/signup")
    public ModelAndView singUp(User user) {
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePassword);
        userRepository.save(user);
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

}

