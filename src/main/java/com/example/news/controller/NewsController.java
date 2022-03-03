package com.example.news.controller;

import com.example.news.dto.NewsDTO;
import com.example.news.service.KeywordService;
import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class NewsController {

    public final NewsService newsService;
    public final KeywordService keywordService;

    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/news")
    public ModelAndView news() {

        modelAndView.addObject("keywords", keywordService.findKeywords());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/newsList")
    public Page<NewsDTO> findNewsPage(@PageableDefault(size = 50, sort = "pubDate", direction = Sort.Direction.DESC) Pageable page) {

        return newsService.findNewsPage(page);
    }

    @ResponseBody
    @PostMapping("/keyword")
    public void inputKeyword(@RequestParam String keyword) {

        keywordService.insertKeywordFirst(keyword);

    }

}
