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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeywordService {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;
    Logger logger = LoggerFactory.getLogger(KeywordService.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public List<KeywordDTO> findKeywords() {
        return KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll());
    }

    public KeywordDTO insertKeywordFirst(String keyword) {
        KeywordDTO keywordDTO = KeywordMapper.INSTANCE.keywordEntityToDTO(insertKeyword(keyword));
        logger.info("최초 수집");
        keywordCollectionFirst(keywordDTO); // 최초 수집
        return keywordDTO;
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
        if (keywordRepository.findByKeyword(Keyword) == null) {
            return false;
        }
        return true;

    }

    @SneakyThrows
    public void keywordCollectionFirst(KeywordDTO keyword) {
        //네이버
        String json = apiExamSearch.search(keyword.getKeyword());
        JsonNode jsonNode = objectMapper.readTree(json);

        if (jsonNode.path("errorMessage").isMissingNode()) {

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

        } else {
            logger.error("Json-error 메세지/기다리는 중");
            Thread.sleep(10000);

            keywordCollectionFirst(keyword);
        }
    }

    @SneakyThrows
    public void keywordCollection(KeywordDTO keyword,LocalDateTime now) {

        logger.info("수집 키워드  : {} ", keyword);
        int start = 1;


        //네이버


        Loop1:
        while (true) {


            logger.info("start 페이지  : {} ", start);
            String json = apiExamSearch.search(keyword.getKeyword(), start);
//            logger.info("{}",json);
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode.path("errorMessage").isMissingNode()) {

                for (JsonNode items : jsonNode.path("items")) {

                    LocalDateTime dateTime = LocalDateTime.parse(items.path("pubDate").asText(), DateTimeFormatter.RFC_1123_DATE_TIME);

                    if (dateTime.compareTo(now) < 0) {
                        logger.info("멈춰!");
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

                if (start > 1000) {
                    break Loop1;
                }

            } else {
                logger.error("Json-error 메세지 / 기다리는 중");
                Thread.sleep(10000);

            }

        }
    }

    public void keywordCollectionAll() {

        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        KeywordMapper.INSTANCE.keywordDTOListToEntityList(keywordRepository.findAll()).stream().forEach(keyword -> {
            keywordCollection(keyword,now);
        });
    }

    public void deleteKeyword(Long keywordIdx) {
        keywordRepository.deleteById(keywordIdx);

    }
}
