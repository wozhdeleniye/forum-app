package ru.tsu.hits.user.Users.converter;


import ru.tsu.hits.common.dto.userDto.CreateUpdateUserDto;
import ru.tsu.hits.common.dto.userDto.UserDto;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.user.Users.entity.UserEntity;

import java.util.Date;
import java.util.UUID;

public class UserConverter {
    public static UserEntity userToEntity(CreateUpdateUserDto dto, String password){
        return new UserEntity(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                dto.getName(),
                dto.getLogin(),
                UserRoleDto.USER,
                dto.getEmail(),
                password,
                dto.getPhone()
        );
    }

    public static UserDto userToDto(UserEntity entity){
        return new UserDto(
                entity.getUuid(),
                entity.getCreationDate(),
                entity.getName(),
                entity.getLogin(),
                entity.getRole(),
                entity.getEmail(),
                entity.getPhone()
        );
    }
}
