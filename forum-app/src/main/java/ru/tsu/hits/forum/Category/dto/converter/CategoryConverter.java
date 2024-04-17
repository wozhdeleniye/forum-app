package ru.tsu.hits.forum.Category.dto.converter;

import ru.tsu.hits.forum.Category.dto.CategoryDto;
import ru.tsu.hits.forum.Category.dto.CreateUpdateCategoryDto;
import ru.tsu.hits.forum.Category.dto.HierarchyDto;
import ru.tsu.hits.forum.Category.entity.CategoryEntity;
import ru.tsu.hits.forum.Search.dto.DataTypeDto;
import ru.tsu.hits.forum.Search.dto.SearchDataDto;

import java.util.*;

public class CategoryConverter {
    public static CategoryEntity categoryToEntity(CreateUpdateCategoryDto dto){
        return new CategoryEntity(
                UUID.randomUUID().toString(),
                new Date(System.currentTimeMillis()),
                null,
                dto.getAuthor(),
                dto.getName(),
                dto.getParentCategory(),
                0
        );
    }

    public static CategoryDto categoryToDto(CategoryEntity entity){
        return new CategoryDto(
            entity.getUuid(),
            entity.getName(),
            entity.getAuthor(),
            entity.getCreationDate(),
            entity.getLastModDate(),
            entity.getParentCategory(),
            entity.getChildCount()
        );
    }

    public static HierarchyDto categoryToHierarchy(CategoryEntity entity){
        return new HierarchyDto(
                entity.getUuid(),
                entity.getChildCount(),
                Collections.emptyList()
        );
    }

    public static SearchDataDto categoryToSearchDto(CategoryEntity entity){
        return new SearchDataDto(
                entity.getUuid(),
                DataTypeDto.CATEGORY
        );
    }
}
