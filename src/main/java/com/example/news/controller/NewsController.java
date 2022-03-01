package com.example.news.controller;

import com.example.news.entity.Keyword;
import com.example.news.entity.News;
import com.example.news.repository.KeywordRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.service.NewsService;
import com.example.news.site.naver.ApiExamSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    public final NewsService newsService;


    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;
    ModelAndView modelAndView = new ModelAndView();
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/news")
    public ModelAndView news() {

        List<Keyword> keywords = keywordRepository.findAll();
        modelAndView.addObject("keywords", keywords);
        modelAndView.addObject("news", newsRepository.findAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/news/collection")
    public ResponseEntity<?> newsCollection() throws JsonProcessingException {

//        keywordRepository.findAll().stream().forEach(keyword -> {

        String json = apiExamSearch.search("컨셉");
        JsonNode jsonNode = objectMapper.readTree(json);


        for (JsonNode items : jsonNode.path("items")) {
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
    @ResponseBody
    @PostMapping("/keyword")
    public void inputKeyword(@RequestParam String keyword){

        newsService.키워드등록(keyword);


//        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    // 키워드 등록 성공이면 다시 불러오게 는 필요 없나 ?
    @GetMapping("/keyword")
    public List<Keyword> findKeyword() {
        return keywordRepository.findAll();
    }

}
