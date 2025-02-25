package com.gucardev.springbootstartertemplate.domain.user.mapper;

import com.gucardev.springbootstartertemplate.domain.user.entity.User;
import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import com.gucardev.springbootstartertemplate.domain.user.model.request.UserCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequest request);

    UserDto toDto(User entity);

}
