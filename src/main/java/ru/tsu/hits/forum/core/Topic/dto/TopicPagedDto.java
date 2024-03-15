package ru.tsu.hits.forum.core.Topic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicPagedDto<T> {
    private Long total;

    private Integer size;

    private Integer pages;

    private List<T> data;
}
