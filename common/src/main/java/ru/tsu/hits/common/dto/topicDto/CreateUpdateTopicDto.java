package ru.tsu.hits.common.dto.topicDto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUpdateTopicDto {
    private String name;

    private String author;

    private String parentCategory;
}
