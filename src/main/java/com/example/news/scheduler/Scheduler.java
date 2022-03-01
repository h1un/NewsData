package com.example.news.scheduler;

import com.example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EnableScheduling
@RequiredArgsConstructor
@Component
public class Scheduler {

    public final NewsService newsService;

    @Scheduled(cron = "* 00 * * * *")
    public void scheduled() {
        newsService.스케줄러키워드수집();
    }

}