package ru.tsu.hits.forum.core.Users.dto.converter;

import ru.tsu.hits.forum.core.Search.dto.DataTypeDto;
import ru.tsu.hits.forum.core.Search.dto.SearchDataDto;
import ru.tsu.hits.forum.core.Topic.dto.CreateUpdateTopicDto;
import ru.tsu.hits.forum.core.Topic.dto.TopicDto;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.core.Users.dto.CreateUpdateUserDto;
import ru.tsu.hits.forum.core.Users.dto.UserDto;
import ru.tsu.hits.forum.core.Users.dto.UserRoleDto;
import ru.tsu.hits.forum.core.Users.entity.UserEntity;

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
