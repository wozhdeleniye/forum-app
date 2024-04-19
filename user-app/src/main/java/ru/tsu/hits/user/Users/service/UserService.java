package ru.tsu.hits.user.Users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.common.dto.exceptionDto.StatusCode;
import ru.tsu.hits.common.dto.userDto.CreateUpdateUserDto;
import ru.tsu.hits.common.dto.userDto.LoginDto;
import ru.tsu.hits.common.dto.userDto.UserDto;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.user.Users.converter.UserConverter;
import ru.tsu.hits.user.Users.entity.UserEntity;
import ru.tsu.hits.user.Users.repository.UserFindRepository;
import ru.tsu.hits.user.Users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFindRepository userFindRepository;

    private final AuthenticationService authService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseEntity<?> create(CreateUpdateUserDto createUpdateUserDto){
        var copyEntity = userRepository.findByLogin(createUpdateUserDto.getLogin());
        if(!copyEntity.isEmpty()) {
            return ResponseEntity
                    .status(400)
                    .body(new StatusCode(
                            "Пользователь уже существует",
                            400
                    ) );
        }
        var entity = UserConverter.userToEntity(createUpdateUserDto, bCryptPasswordEncoder.encode(createUpdateUserDto.getPassword()));
        var savedEntity = userRepository.save(entity);

        return ResponseEntity
                .status(200)
                .body(authService.generateRegToken(savedEntity));
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto){
        return ResponseEntity
                .status(200)
                .body(authService.generateJwt(loginDto));
    }

    @Transactional
    public ResponseEntity<?> changeRole(String id, UserRoleDto roleToChange){
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));;
        entity.setRole(roleToChange);
        var savedEntity = userRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(UserConverter.userToDto(savedEntity));
    }

    @Transactional
    public ResponseEntity<?> blockUser(String id){
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));;
        entity.setRole(UserRoleDto.BlOCKED);
        var savedEntity = userRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(UserConverter.userToDto(savedEntity));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(){
        return userFindRepository.findAll().stream()
                .map(UserConverter:: userToDto)
                .collect(Collectors.toList());
    }
}
