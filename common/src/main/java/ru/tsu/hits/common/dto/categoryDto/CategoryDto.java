package ru.tsu.hits.common.dto.categoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String id;

    private String name;

    private String author;

    private Date creationDate;

    private Date lastModDate;

    private String parentCategory;

    private Integer childCount;
}
