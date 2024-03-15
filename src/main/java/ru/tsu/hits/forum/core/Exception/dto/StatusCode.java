package ru.tsu.hits.forum.core.Exception.dto;

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
