package com.example.news.service;

import com.example.news.dto.KeywordDTO;
import com.example.news.dto.NewsDTO;
import com.example.news.mapper.KeywordMapper;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.KeywordRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.site.naver.ApiExamSearch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsService {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<KeywordDTO> findKeywords() {
        return KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll());
    }

    public Page<NewsDTO> findNewsPaging(Pageable page) {
        return NewsMapper.INSTANCE.newsEntityPageToNewsDTOPage(newsRepository.findAll(page));
    }

    public void 키워드등록(String keyword) {

        KeywordDTO keyword1 = KeywordMapper.INSTANCE.keywordEntityToDTO(
                keywordRepository.save(
                        KeywordMapper.INSTANCE.keywordDTOToEntity(KeywordDTO.builder().keyword(keyword).build()
                        )
                ));

        첫키워드수집(keyword1); // 최초 수집

    }

    @SneakyThrows
    public void 첫키워드수집(KeywordDTO keyword) {
        //네이버
        String json = apiExamSearch.search(keyword.getKeyword());
        JsonNode jsonNode = objectMapper.readTree(json);

        for (JsonNode items : jsonNode.path("items")) {

            LocalDateTime dateTime = LocalDateTime.parse(items.path("pubDate").asText(), DateTimeFormatter.RFC_1123_DATE_TIME);

            NewsDTO newsDTO = NewsDTO.builder()
                    .keyword(keyword)
                    .link(items.path("link").asText())
                    .title(items.path("title").asText())
                    .pubDate(dateTime)
                    .description(items.path("description").asText())
                    .build();
            newsRepository.save(NewsMapper.INSTANCE.newsDTOtoEntity(newsDTO));
        }
    }

    @SneakyThrows
    public void 키워드수집(KeywordDTO keyword) {
        //네이버
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        int start = 1;
        Loop1:
        while (true) {

            String json = apiExamSearch.search(keyword.getKeyword(), start);
            JsonNode jsonNode = objectMapper.readTree(json);

            Loop2:
            for (JsonNode items : jsonNode.path("items")) {

                LocalDateTime dateTime = LocalDateTime.parse(items.path("pubDate").asText(), DateTimeFormatter.RFC_1123_DATE_TIME);

                if (dateTime.compareTo(now) < 0) {
                    break Loop1;
                }

                NewsDTO newsDTO = NewsDTO.builder()
                        .keyword(keyword)
                        .link(items.path("link").asText())
                        .title(items.path("title").asText())
                        .pubDate(dateTime)
                        .description(items.path("description").asText())
                        .build();
                newsRepository.save(NewsMapper.INSTANCE.newsDTOtoEntity(newsDTO));
            }

            start += 100;
        }
    }

    public void 스케줄러키워드수집() {
        KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll()).stream().forEach(keyword -> {
            키워드수집(keyword);
        });
    }
}
