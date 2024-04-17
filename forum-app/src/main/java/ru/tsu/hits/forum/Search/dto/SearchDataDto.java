package ru.tsu.hits.forum.Search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDataDto {
    private String id;

    private DataTypeDto type;


}
