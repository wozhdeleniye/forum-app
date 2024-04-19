package ru.tsu.hits.forum.Topic.converter;

import ru.tsu.hits.common.dto.searchDto.DataTypeDto;
import ru.tsu.hits.common.dto.searchDto.SearchDataDto;
import ru.tsu.hits.common.dto.topicDto.CreateUpdateTopicDto;
import ru.tsu.hits.common.dto.topicDto.TopicDto;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;

import java.util.Date;
import java.util.UUID;

public class TopicConverter {
    public static TopicEntity topicToEntity(CreateUpdateTopicDto dto){
        return new TopicEntity(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                null,
                dto.getAuthor(),
                dto.getName(),
                dto.getParentCategory()
        );
    }

    public static TopicDto topicToDto(TopicEntity entity){
        return new TopicDto(
            entity.getUuid(),
            entity.getName(),
            entity.getAuthor(),
            entity.getCreationDate(),
            entity.getLastModDate(),
            entity.getParentCategory()
        );
    }

    public static SearchDataDto topicToSearchDto(TopicEntity entity){
        return new SearchDataDto(
                entity.getUuid(),
                DataTypeDto.TOPIC
        );
    }
}
