package ru.tsu.hits.common.dto.searchDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDataDto {
    private String id;

    private DataTypeDto type;


}
