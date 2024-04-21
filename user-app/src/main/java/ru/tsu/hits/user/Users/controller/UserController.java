package ru.tsu.hits.user.Users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.userDto.CreateUpdateUserDto;
import ru.tsu.hits.common.dto.userDto.GiveRoleDto;
import ru.tsu.hits.common.dto.userDto.LoginDto;
import ru.tsu.hits.common.dto.userDto.UserDto;
import ru.tsu.hits.user.Users.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> create(@RequestBody CreateUpdateUserDto createUpdateUserDto){
        return userService.create(createUpdateUserDto);
    }

    @PutMapping("give-role")
    public ResponseEntity<?> giveRole(@RequestBody GiveRoleDto dto, Authentication authentication){
        return userService.changeRole( dto.getId(), dto.getRoleForUser(), authentication);
    }//в этой функции можно и заблокировать человека

    @GetMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        return userService.login(dto);
    }

    @GetMapping("get")
    public ResponseEntity<?> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("edit/{id}")
    public ResponseEntity<?> edituser(Authentication auth, @PathVariable UUID id, @RequestBody CreateUpdateUserDto dto){
        return userService.editUser(auth, id.toString(), dto);
    }

    @GetMapping("login-by-token")
    public ResponseEntity<?> loginByToken(@RequestBody String token){
        return userService.loginByToken(token);
    }

    @GetMapping("create-refresh-token")
    public ResponseEntity<?> createRefreshToken(Authentication auth){
        return userService.createRefreshToken(auth);
    }

}