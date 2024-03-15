package ru.tsu.hits.forum.core.Users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;

    private Date creationDate;

    private String login;

    private UserRoleDto role;
}
