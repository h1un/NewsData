package com.example.news.controller;

import com.example.news.dto.UserDTO;
import com.example.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@RestController
public class UserController {
    public final UserService userService;


    @GetMapping("/login")
    public ModelAndView loginFrom() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/signup")
    public ModelAndView singUpFrom() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/signup")
    public ModelAndView singUp(UserDTO user) {
        ModelAndView modelAndView = new ModelAndView();
        userService.insertUser(user);
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/signup/{userId}")
    public boolean findUserId(@PathVariable String userId) {

        return userService.checkUserId(userId);

    }

    @ResponseBody
    @GetMapping("/auth/kakao/login")
    public ModelAndView kakaoLogin(String code) {
        ModelAndView modelAndView = new ModelAndView();
        userService.kakaoLogin(code);
        modelAndView.setViewName("redirect:/");
        return modelAndView;

    }


}

