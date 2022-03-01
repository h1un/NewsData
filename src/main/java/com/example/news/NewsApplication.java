package com.example.news;

import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@SpringBootApplication
public class NewsApplication {

    public final NewsService newsService;

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);

    }

    @EventListener(ApplicationReadyEvent.class)
    void 키워드(){
        newsService.키워드등록("선거");
        newsService.키워드등록("코로나");
    }

}
