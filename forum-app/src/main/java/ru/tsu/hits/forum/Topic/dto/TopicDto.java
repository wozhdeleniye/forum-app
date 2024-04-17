package ru.tsu.hits.forum.Topic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private String id;

    private String name;

    private String author;

    private Date creationDate;

    private Date lastModDate;

    private String parentCategory;
}
