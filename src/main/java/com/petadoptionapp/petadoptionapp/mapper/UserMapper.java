package com.petadoptionapp.petadoptionapp.mapper;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.bean.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User user(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto map(User user);


}
