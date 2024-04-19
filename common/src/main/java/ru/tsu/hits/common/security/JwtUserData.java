package ru.tsu.hits.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class JwtUserData {

    private final UUID id;

    private final String login;

    private final String name;

    private final String role;
}
