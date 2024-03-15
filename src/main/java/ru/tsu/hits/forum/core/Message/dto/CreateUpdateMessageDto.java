package ru.tsu.hits.forum.core.Message.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUpdateMessageDto {
    private String text;

    private String author;

    private UUID topic;
}
