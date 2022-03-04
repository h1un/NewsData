package com.example.news;

import com.example.news.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@RequiredArgsConstructor
@SpringBootApplication
public class NewsApplication {

    public final KeywordService keywordService;

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    void keywordCollection() {
        keywordService.insertKeywordFirst("선거");
        keywordService.insertKeywordFirst("코로나");
    }

}
