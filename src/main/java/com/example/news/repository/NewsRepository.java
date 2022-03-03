package com.example.news.repository;

import com.example.news.entity.KeywordEntity;
import com.example.news.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Override
    Page<NewsEntity> findAll(Pageable pageable);

    Page<NewsEntity> findAllByKeyword(Pageable pageable, KeywordEntity keywordEntity);
}
