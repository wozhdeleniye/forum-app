package ru.tsu.hits.forum.Message.converter;

import ru.tsu.hits.common.dto.messageDto.CreateUpdateMessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageSearchResponseDto;
import ru.tsu.hits.forum.Message.entity.MessageEntity;
import ru.tsu.hits.common.dto.searchDto.DataTypeDto;
import ru.tsu.hits.common.dto.searchDto.SearchDataDto;

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
