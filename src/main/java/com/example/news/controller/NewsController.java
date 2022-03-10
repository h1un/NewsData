package com.example.news.controller;

import com.example.news.config.auth.PrincipalDetail;
import com.example.news.dto.KeywordDTO;
import com.example.news.dto.NewsDTO;
import com.example.news.service.KeywordService;
import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {

    public final NewsService newsService;
    public final KeywordService keywordService;


    @GetMapping({"/news", "/"})
    public ModelAndView news(@AuthenticationPrincipal PrincipalDetail principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("keywords", keywordService.findKeywords());
        modelAndView.setViewName("index");
        modelAndView.addObject("userName", principal.getUser().getUserName());
        return modelAndView;
    }

    @GetMapping("/newsList")
    public Page<NewsDTO> findNewsPage(@PageableDefault(size = 50, sort = "pubDate", direction = Sort.Direction.DESC) Pageable page) {

        return newsService.findNewsPage(page);
    }

    @GetMapping("/newsList/{keyword}")
    public Page<NewsDTO> findNewsPageByKeyword(@PageableDefault(size = 50, sort = "pubDate", direction = Sort.Direction.DESC) Pageable page, @PathVariable String keyword) {

        return newsService.findNewsPageByKeyword(page, keyword);
    }


    @ResponseBody
    @GetMapping("/keyword")
    public List<KeywordDTO> findAllKeyword() {

        return keywordService.findKeywords();

    }

    @ResponseBody
    @GetMapping("/keyword/{keyword}")
    public boolean findKeyword(@PathVariable String keyword) {

        return keywordService.checkKeyword(keyword);

    }

    @ResponseBody
    @PostMapping("/keyword")
    public KeywordDTO inputKeyword(@RequestParam String keyword) {

        return keywordService.insertKeywordFirst(keyword);

    }

    @ResponseBody
    @DeleteMapping("/keyword/{keywordIdx}")
    public void deleteKeyword(@PathVariable Long keywordIdx) {

        keywordService.deleteKeyword(keywordIdx);

    }

}
