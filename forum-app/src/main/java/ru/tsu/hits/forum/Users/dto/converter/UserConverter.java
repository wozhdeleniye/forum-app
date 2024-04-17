package ru.tsu.hits.forum.Users.dto.converter;

import ru.tsu.hits.forum.Search.dto.DataTypeDto;
import ru.tsu.hits.forum.Search.dto.SearchDataDto;
import ru.tsu.hits.forum.Topic.dto.CreateUpdateTopicDto;
import ru.tsu.hits.forum.Topic.dto.TopicDto;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.Users.dto.CreateUpdateUserDto;
import ru.tsu.hits.forum.Users.dto.UserDto;
import ru.tsu.hits.forum.Users.dto.UserRoleDto;
import ru.tsu.hits.forum.Users.entity.UserEntity;

import java.util.Date;
import java.util.UUID;

public class UserConverter {
    public static UserEntity userToEntity(CreateUpdateUserDto dto){
        return new UserEntity(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                dto.getLogin(),
                UserRoleDto.USER
        );
    }

    public static UserDto userToDto(UserEntity entity){
        return new UserDto(
                entity.getUuid(),
                entity.getCreationDate(),
                entity.getLogin(),
                entity.getRole()
        );
    }
}
