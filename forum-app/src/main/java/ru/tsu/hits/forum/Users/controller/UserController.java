package ru.tsu.hits.forum.Users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.forum.Users.dto.CreateUpdateUserDto;
import ru.tsu.hits.forum.Users.dto.GiveRoleDto;
import ru.tsu.hits.forum.Users.dto.UserDto;
import ru.tsu.hits.forum.Users.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUpdateUserDto createUpdateUserDto){
        return userService.create(createUpdateUserDto);
    }

    @PutMapping
    public UserDto giveRole(@RequestBody GiveRoleDto dto){
        return userService.giveRole( dto.getId(), dto.getUserId(),dto.getRoleForUser());
    }
}