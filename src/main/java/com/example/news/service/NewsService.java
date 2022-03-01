package com.example.news.service;

import com.example.news.entity.Keyword;
import com.example.news.entity.News;
import com.example.news.repository.KeywordRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.site.naver.ApiExamSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class NewsService {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;

    ObjectMapper objectMapper = new ObjectMapper();

    public void 키워드등록(String keyword) {

        Keyword keyword1 = keywordRepository.save(Keyword.builder().keyword(keyword).build());
        키워드수집(keyword1); // 최초 수집

    }

    @SneakyThrows
    public void 키워드수집(Keyword keyword) {
        //네이버
        String json = apiExamSearch.search(keyword.getKeyword());
        JsonNode jsonNode = objectMapper.readTree(json);


        for (JsonNode items : jsonNode.path("items")) {

            LocalDateTime dateTime = LocalDateTime.parse(items.path("pubDate").asText(), DateTimeFormatter.RFC_1123_DATE_TIME);

            News news = News.builder()
                    .keyword(keyword)
                    .link(items.path("link").asText())
                    .title(items.path("title").asText())
                    .pubDate(dateTime)
                    .description(items.path("description").asText())
                    .build();
            newsRepository.save(news);
        }

    }

    public void 스케줄러키워드수집() {

        keywordRepository.findAll().stream().forEach(keyword -> {
            키워드수집(keyword);
        });
    }
}
