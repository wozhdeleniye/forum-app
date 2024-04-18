package ru.tsu.hits.common.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiveRoleDto {

    private String id;

    private String userId;

    private UserRoleDto roleForUser;
}
