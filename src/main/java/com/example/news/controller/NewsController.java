package com.example.news.controller;

import com.example.news.entity.Keyword;
import com.example.news.entity.News;
import com.example.news.repository.KeywordRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.site.naver.ApiExamSearch;
import com.example.news.site.naver.NaverNewsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;
    ModelAndView modelAndView = new ModelAndView();
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/test")
    public ModelAndView jointestHtml() {

        List<Keyword> keywords = keywordRepository.findAll();
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("news", newsRepository.findAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/keyword")
    public List<Keyword> findKeyword() {

        keywordRepository.findAll().stream().forEach(System.out::println);
        return keywordRepository.findAll();
    }

    @GetMapping("/news/collection")
    public ResponseEntity<?> newsCollection() throws JsonProcessingException {

//        keywordRepository.findAll().stream().forEach(keyword -> {
        String json = apiExamSearch.search("컨셉");

//                NaverNewsVo naverNewsVo = objectMapper.readValue(json, NaverNewsVo.class);
        JsonNode jsonNode = objectMapper.readTree(json);

/*
        News news = News.builder()
//                            .keyword(keyword)
                .link(items.getOriginallink())
                .title(items.getTitle())
                .description(items.getDescription())
                .build();
*/

        for(JsonNode items : jsonNode.path("items")) {
            News news = News.builder()
//                            .keyword(keyword)
                    .link(items.path("link").asText())
                    .title(items.path("title").asText())
                    .description(items.path("description").asText())
                    .build();
            newsRepository.save(news);
        }


//        });

        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

}
