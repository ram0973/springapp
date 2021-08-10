package com.me.springapp.dto;

import com.me.springapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToDto(User user);

    User userFromDto(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserFromDto(UserDTO userDTO, @MappingTarget User user);
}
