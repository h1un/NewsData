package com.example.news.repository;

import com.example.news.entity.KeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {

    KeywordEntity findByKeyword(String keyword);

}
