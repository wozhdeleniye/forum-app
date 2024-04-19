package ru.tsu.hits.common.dto.userDto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CreateUpdateUserDto {
    private String login;

    private String email;

    private String password;

    private String phone;

    private String name;
}
