package ru.tsu.hits.forum.Category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyDto {

    private String id;

    private Integer childrenCount;

    private List<HierarchyDto> hierarchy;
}
