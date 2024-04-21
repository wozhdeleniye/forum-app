package ru.tsu.hits.user.Users.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tsu.hits.common.dto.userDto.LoginDto;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.common.security.exception.BlockedException;
import ru.tsu.hits.common.security.exception.UnauthorizedException;
import ru.tsu.hits.common.security.props.SecurityProps;
import ru.tsu.hits.user.Users.converter.UserConverter;
import ru.tsu.hits.user.Users.entity.UserEntity;
import ru.tsu.hits.user.Users.repository.TokenRepository;
import ru.tsu.hits.user.Users.repository.UserFindRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SecurityProps securityProps;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserFindRepository userFindRepository;

    private final TokenRepository tokenRepository;
    /**
     * Генерация токена
     *
     * @param loginDto логин и пароль (данные входа в систему)
     * @return строка с jwt
     */
    public String generateJwt(LoginDto loginDto) {
        var user = userFindRepository.findFirstByLogin(loginDto.getLogin())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Incorrect login: " + loginDto.getLogin()));
        if(user.getRole() == UserRoleDto.BlOCKED){
            throw new BlockedException("You were blocked by admin");
        }
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Incorrect password");
        }
        var key = Keys.hmacShaKeyFor(securityProps.getJwtToken().getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getName())
                .setClaims(Map.of(
                        "login", user.getLogin(),
                        "name", user.getName(),
                        "role", user.getRole().toString()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + securityProps.getJwtToken().getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateNewAccessToken(UserEntity userEntity) {
        var key = Keys.hmacShaKeyFor(securityProps.getJwtToken().getSecret().getBytes(StandardCharsets.UTF_8));
        var token = Jwts.builder()
                .setSubject(userEntity.getName())
                .setClaims(Map.of(
                        "login", userEntity.getLogin(),
                        "name", userEntity.getName(),
                        "role", userEntity.getRole().toString()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + securityProps.getJwtToken().getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        tokenRepository.save(UserConverter.tokenToEntity(token, userEntity.getLogin()));
        return token;
    }

    public String generateTokenByToken(String token) {
        var refToken = tokenRepository.findFirstByToken(token)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Incorrect refresh token"));

        var user = userFindRepository.findFirstByLogin(refToken.getLogin())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Incorrect login in token"));

        var key = Keys.hmacShaKeyFor(securityProps.getJwtToken().getSecret().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(user.getName())
                .setClaims(Map.of(
                        "login", user.getLogin(),
                        "name", user.getName(),
                        "role", user.getRole().toString()
                ))
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(currentTimeMillis() + securityProps.getJwtToken().getExpiration()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


}
