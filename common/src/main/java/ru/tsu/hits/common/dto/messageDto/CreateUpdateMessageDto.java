package ru.tsu.hits.common.dto.messageDto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUpdateMessageDto {
    private String text;

    private String author;

    private UUID topic;
}
