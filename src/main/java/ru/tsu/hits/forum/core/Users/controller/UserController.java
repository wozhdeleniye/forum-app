package ru.tsu.hits.forum.core.Users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.forum.core.Topic.dto.CreateUpdateTopicDto;
import ru.tsu.hits.forum.core.Topic.dto.TopicDto;
import ru.tsu.hits.forum.core.Topic.service.TopicService;
import ru.tsu.hits.forum.core.Users.dto.CreateUpdateUserDto;
import ru.tsu.hits.forum.core.Users.dto.GiveRoleDto;
import ru.tsu.hits.forum.core.Users.dto.UserDto;
import ru.tsu.hits.forum.core.Users.service.UserService;

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