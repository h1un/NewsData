package com.example.news.scheduler;

import com.example.news.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class Scheduler {

    public final KeywordService keywordService;

    @Scheduled(cron = "00 07 * * * *")
    public void scheduled() {
        keywordService.keywordCollectionAll();
    }

}
