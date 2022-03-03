package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class KeywordDTO {
    private Long keywordIdx;
    private String keyword;
}
