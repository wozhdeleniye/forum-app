package ru.tsu.hits.common.dto.userDto;

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

    private String name;

    private String login;

    private UserRoleDto role;

    private String email;

    private String phone;
}
