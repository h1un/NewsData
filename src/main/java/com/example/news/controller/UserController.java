package com.example.news.controller;

import com.example.news.entity.Keyword;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/login")
    public ModelAndView loginFrom() {

        modelAndView.setViewName("login");
        return modelAndView;
    }


    @PostMapping("/login")
    public ModelAndView login() {

        modelAndView.setViewName("login");
        return modelAndView;
    }

}

