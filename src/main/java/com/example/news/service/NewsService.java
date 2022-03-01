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

@RequiredArgsConstructor
@Service
public class NewsService {

    public final KeywordRepository keywordRepository;
    public final NewsRepository newsRepository;
    public final ApiExamSearch apiExamSearch;

    ObjectMapper objectMapper = new ObjectMapper();

    public void 키워드등록(String keyword) {

        keywordRepository.save(Keyword.builder().keyword(keyword).build());
        키워드수집(keyword); // 최초 수집

    }

    @SneakyThrows
    public void 키워드수집(String keyword) {
        //        keywordRepository.findAll().stream().forEach(keyword -> {

        String json = apiExamSearch.search("컨셉");
        JsonNode jsonNode = objectMapper.readTree(json);


        for (JsonNode items : jsonNode.path("items")) {
            News news = News.builder()
//                            .keyword(keyword)
                    .link(items.path("link").asText())
                    .title(items.path("title").asText())
                    .description(items.path("description").asText())
                    .build();
            newsRepository.save(news);
        }


//        });
    }
}
