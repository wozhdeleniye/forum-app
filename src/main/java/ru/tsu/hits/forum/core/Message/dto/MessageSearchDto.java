package ru.tsu.hits.forum.core.Message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSearchDto {
    private String author = "";

    private String text = "";

    private Date creationDateFrom;

    private Date creationDateTo;
}
