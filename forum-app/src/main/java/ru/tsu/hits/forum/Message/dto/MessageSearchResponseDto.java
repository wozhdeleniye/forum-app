package ru.tsu.hits.forum.Message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSearchResponseDto {

    private String author;

    private String text;

    private Date creationDate;
}
