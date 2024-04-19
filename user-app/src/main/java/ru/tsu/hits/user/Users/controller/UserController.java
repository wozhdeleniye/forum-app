package ru.tsu.hits.user.Users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.userDto.CreateUpdateUserDto;
import ru.tsu.hits.common.dto.userDto.GiveRoleDto;
import ru.tsu.hits.common.dto.userDto.LoginDto;
import ru.tsu.hits.common.dto.userDto.UserDto;
import ru.tsu.hits.user.Users.service.UserService;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUpdateUserDto createUpdateUserDto){
        return userService.create(createUpdateUserDto);
    }

    @PutMapping
    public ResponseEntity<?> giveRole(@RequestBody GiveRoleDto dto){
        return userService.changeRole( dto.getId(), dto.getRoleForUser());
    }

    @GetMapping
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        return userService.login(dto);
    }
}