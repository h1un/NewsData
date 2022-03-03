package com.example.news.mapper;

import com.example.news.dto.UserDTO;
import com.example.news.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userDTOToEntity(UserDTO userDTO);

    UserDTO userEntityToDTO(UserEntity userEntity);
}
