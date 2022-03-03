package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_idx")
    private Long newsIdx;

    private String site;
    @ManyToOne(fetch = FetchType.EAGER)//연관관계맺음 Many = Many, User =One
    @JoinColumn(name = "keyword_idx")
    private KeywordEntity keyword;
    private String title;
    private String link;
    private String description;
    private LocalDateTime pubDate;

}
