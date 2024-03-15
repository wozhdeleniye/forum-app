package ru.tsu.hits.forum.core.Message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String id;

    private Date creationDate;

    private Date lastModDate;

    private String author;

    private String text;

    private UUID topic;

    private String category;
}
