package com.example.news.service;

import com.example.news.dto.KeywordDTO;
import com.example.news.dto.NewsDTO;
import com.example.news.entity.KeywordEntity;
import com.example.news.mapper.KeywordMapper;
import com.example.news.mapper.NewsMapper;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<KeywordDTO> findKeywords() {
        return KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll());
    }

    public void insertKeywordFirst(String keyword) {

        keywordCollectionFirst(KeywordMapper.INSTANCE.keywordEntityToDTO(insertKeyword(keyword))); // 최초 수집

    }

    public KeywordEntity insertKeyword(String keyword) {
        return keywordRepository.save(
                KeywordMapper.INSTANCE.keywordDTOToEntity(KeywordDTO.builder().keyword(keyword).build()
                )
        );
    }

    public KeywordDTO findKeywordByKeyword(String Keyword) {
        return KeywordMapper.INSTANCE.keywordEntityToDTO(keywordRepository.findByKeyword(Keyword));
    }

    public boolean checkKeyword(String Keyword) {
        if(keywordRepository.findByKeyword(Keyword)==null){
            return false;
        }
        return true;

    }

    @SneakyThrows
    public void keywordCollectionFirst(KeywordDTO keyword) {
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
    public void keywordCollection(KeywordDTO keyword) {
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

    public void keywordCollectionAll() {
        KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll()).stream().forEach(keyword -> {
            keywordCollection(keyword);
        });
    }
}
