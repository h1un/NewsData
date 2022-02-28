package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    ModelAndView mv = new ModelAndView();

    @GetMapping("/test")
    public ModelAndView jointestHtml() {

        mv.setViewName("index");
        return mv;
    }

}

