package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewsDTO {

    private Long newsIdx;

    private String site;
    private KeywordDTO keyword;
    private String title;
    private String link;
    private String description;
    private LocalDateTime pubDate;

}
