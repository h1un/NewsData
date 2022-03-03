package com.example.news.controller;

import com.example.news.dto.NewsDTO;
import com.example.news.entity.NewsEntity;
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


    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/news")
    public ModelAndView news() {

        modelAndView.addObject("keywords", newsService.findKeywords());
//        modelAndView.addObject("news", newsRepository.findAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/newsList")
    public Page<NewsDTO> newsPage(@PageableDefault(size = 50, sort = "pubDate", direction = Sort.Direction.DESC) Pageable page) {

        return newsService.findNewsPaging(page);
    }

//    @GetMapping("/news/collection")
//    public ResponseEntity<?> newsCollection() throws JsonProcessingException {
//
////        keywordRepository.findAll().stream().forEach(keyword -> {
//
//        String json = apiExamSearch.search("컨셉");
//        JsonNode jsonNode = objectMapper.readTree(json);
//
//
//        for (JsonNode items : jsonNode.path("items")) {
//            News news = News.builder()
////                            .keyword(keyword)
//                    .link(items.path("link").asText())
//                    .title(items.path("title").asText())
//                    .description(items.path("description").asText())
//                    .build();
//            newsRepository.save(news);
//        }
//
//
////        });
//
//        return new ResponseEntity<String>("ok", HttpStatus.OK);
//    }

    @ResponseBody
    @PostMapping("/keyword")
    public void inputKeyword(@RequestParam String keyword) {

        newsService.키워드등록(keyword);

    }
}
