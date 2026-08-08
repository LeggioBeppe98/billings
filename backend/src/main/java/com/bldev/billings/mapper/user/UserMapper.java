package com.bldev.billings.mapper.user;

import com.bldev.billings.dto.user.UserCreateDto;
import com.bldev.billings.dto.user.UserDetailDto;
import com.bldev.billings.dto.user.UserListDto;
import com.bldev.billings.dto.user.UserUpdateDto;
import com.bldev.billings.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserListDto toListDto(User user);
    UserDetailDto toDetailDto(User user);
    User toEntity(UserCreateDto dto);
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);

}

