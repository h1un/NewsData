package com.example.news.service;

import com.example.news.dto.NewsDTO;
import com.example.news.mapper.NewsMapper;
import com.example.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewsService {

    public final NewsRepository newsRepository;

    public Page<NewsDTO> findNewsPage(Pageable page) {
        return NewsMapper.INSTANCE.newsEntityPageToNewsDTOPage(newsRepository.findAll(page));
    }

}
