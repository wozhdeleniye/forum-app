package ru.tsu.hits.common.dto.exceptionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusCode {
    private String message;
    private Integer code;
}
