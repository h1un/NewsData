package com.example.news.mapper;

import com.example.news.dto.NewsDTO;
import com.example.news.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    NewsEntity newsDTOtoEntity(NewsDTO newsDTO);

    NewsDTO newsEntityToDTO(NewsEntity newsEntity);

    default Page<NewsDTO> newsEntityPageToNewsDTOPage(Page<NewsEntity> newsEntityList) {
        return newsEntityList.map(this::newsEntityToDTO);
    }
}
