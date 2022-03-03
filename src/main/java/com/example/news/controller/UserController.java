package com.example.news.controller;

import com.example.news.dto.UserDTO;
import com.example.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class UserController {
    public final UserService userService;
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
    public ModelAndView singUp(UserDTO user) {
        userService.insertUser(user);
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

}

