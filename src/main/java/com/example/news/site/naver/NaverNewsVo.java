package com.example.news.site.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class NaverNewsVo {

    String lastBuildDate;
    String total;
    String start;
    String display;
    List<Items> items;

    @ToString
    @Getter
    @NoArgsConstructor
    public class Items {
        String title;
        String originallink;
        String link;
        String description;
        String pubDate;
    }
}
