package com.example.news.scheduler;

import com.example.news.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@RequiredArgsConstructor
@Component
public class Scheduler {

    public final KeywordService keywordService;

    @Scheduled(cron = "* 00 * * * *")
    public void scheduled() {
        keywordService.keywordCollectionAll();
    }

}
