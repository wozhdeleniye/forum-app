package ru.tsu.hits.forum.Topic.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUpdateTopicDto {
    private String name;

    private String author;

    private String parentCategory;
}
