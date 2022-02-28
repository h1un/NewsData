package com.example.news.controller;

import com.example.news.entity.Keyword;
import com.example.news.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    public final KeywordRepository keywordRepository;

    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/test")
    public ModelAndView jointestHtml() {

        modelAndView.addObject("keywords", keywordRepository.findAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/keyword")
    public List<Keyword> findKeyword() {

        keywordRepository.findAll().stream().forEach(System.out::println);
        return keywordRepository.findAll();
    }

}
