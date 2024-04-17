package ru.tsu.hits.forum.Message.dto.converter;

import ru.tsu.hits.forum.Message.dto.CreateUpdateMessageDto;
import ru.tsu.hits.forum.Message.dto.MessageDto;
import ru.tsu.hits.forum.Message.dto.MessageSearchResponseDto;
import ru.tsu.hits.forum.Message.entity.MessageEntity;
import ru.tsu.hits.forum.Search.dto.DataTypeDto;
import ru.tsu.hits.forum.Search.dto.SearchDataDto;
import ru.tsu.hits.forum.Topic.dto.CreateUpdateTopicDto;
import ru.tsu.hits.forum.Topic.dto.TopicDto;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;

import java.util.Date;
import java.util.UUID;

public class MessageConverter {
    public static MessageEntity messageToEntity(CreateUpdateMessageDto dto, String categoryId){
        return new MessageEntity(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                null,
                dto.getAuthor(),
                dto.getText(),
                dto.getTopic(),
                categoryId
        );
    }

    public static MessageDto messageToDto(MessageEntity entity){
        return new MessageDto(
                entity.getUuid(),
                entity.getCreationDate(),
                entity.getLastModDate(),
                entity.getAuthor(),
                entity.getText(),
                entity.getTopic(),
                entity.getCategory()
        );
    }

    public static SearchDataDto messageToSearchDto(MessageEntity entity){
        return new SearchDataDto(
                entity.getUuid(),
                DataTypeDto.MESSAGE
        );
    }

    public static MessageSearchResponseDto messageToResponseDto(MessageEntity entity){
        return new MessageSearchResponseDto(
                entity.getAuthor(),
                entity.getText(),
                entity.getCreationDate()
        );
    }
}
