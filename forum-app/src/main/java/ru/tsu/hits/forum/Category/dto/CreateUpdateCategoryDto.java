package ru.tsu.hits.forum.Category.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUpdateCategoryDto {

    private String name;

    private String author;

    private String parentCategory;
}
