package com.example.news.mapper;

import com.example.news.dto.KeywordDTO;
import com.example.news.entity.KeywordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KeywordMapper {

    KeywordMapper INSTANCE = Mappers.getMapper(KeywordMapper.class);

    KeywordEntity keywordDTOToEntity(KeywordDTO keywordDto);

    KeywordDTO keywordEntityToDTO(KeywordEntity keywordEntity);

    List<KeywordDTO> keywordDTOListToEntityList(List<KeywordEntity> keywordEntityList);
}
