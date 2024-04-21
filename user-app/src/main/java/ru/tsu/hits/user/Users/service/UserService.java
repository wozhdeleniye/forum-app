package ru.tsu.hits.user.Users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.common.dto.exceptionDto.StatusCode;
import ru.tsu.hits.common.dto.userDto.CreateUpdateUserDto;
import ru.tsu.hits.common.dto.userDto.LoginDto;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.common.security.JwtUserData;
import ru.tsu.hits.common.security.exception.ForbiddenException;
import ru.tsu.hits.user.Users.converter.UserConverter;
import ru.tsu.hits.user.Users.repository.UserFindRepository;
import ru.tsu.hits.user.Users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.tsu.hits.user.Users.converter.UserConverter.userToDto;

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
                .body(authService.generateNewAccessToken(savedEntity));
    }

    @Transactional
    public ResponseEntity<?> login(LoginDto loginDto){
        return ResponseEntity
                .status(200)
                .body(authService.generateJwt(loginDto));
    }

    @Transactional
    public ResponseEntity<?> changeRole(String id, UserRoleDto roleToChange, Authentication authentication){
        var user = (JwtUserData)authentication.getPrincipal();
        if(!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())){
            throw new ForbiddenException("Вы не админ, чтобы менять роль");
        }
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));;
        entity.setRole(roleToChange);
        var savedEntity = userRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(userToDto(savedEntity));
    }


    @Transactional//не используется, блокировать можно через changeRole
    public ResponseEntity<?> blockUser(String id){
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));;
        entity.setRole(UserRoleDto.BlOCKED);
        var savedEntity = userRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(userToDto(savedEntity));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<?>> getUsers(){
        return ResponseEntity
                .status(200)
                .body(userFindRepository.findAll().stream()
                        .map(UserConverter:: userToDto)
                        .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> editUser(Authentication auth, String userToEdit, CreateUpdateUserDto dto){
        var user = (JwtUserData)auth.getPrincipal();
        if(user.getId().toString().equals(userToEdit)){
            var entity  = userRepository.findById(userToEdit)
                    .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

            entity.setEmail(dto.getEmail());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setPhone(dto.getPhone());
            entity.setName(dto.getName());

            var savedEntity = userRepository.save(entity);
            return ResponseEntity
                    .status(200)
                    .body(userToDto(savedEntity));
        }
        else if(user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())){
            var entity  = userRepository.findById(userToEdit)
                    .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

            entity.setEmail(dto.getEmail());
            entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            entity.setPhone(dto.getPhone());
            entity.setName(dto.getName());

            var savedEntity = userRepository.save(entity);
            return ResponseEntity
                    .status(200)
                    .body(userToDto(savedEntity));
        }
        else {
            throw new ForbiddenException("Вы не имеете прав менять этого пользователя");
        }

    }

    @Transactional
    public ResponseEntity<?> loginByToken(String token){
        return ResponseEntity
                .status(200)
                .body(authService.generateTokenByToken(token));
    }

    @Transactional
    public ResponseEntity<?> createRefreshToken(Authentication auth){
        var user = (JwtUserData)auth.getPrincipal();
        var entity  = userRepository.findById(user.getLogin())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        return ResponseEntity
                .status(200)
                .body(authService.generateNewAccessToken(entity));
    }
}
