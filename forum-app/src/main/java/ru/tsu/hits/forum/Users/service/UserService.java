package ru.tsu.hits.forum.Users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.Exception.dto.StatusCode;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.forum.Topic.dto.converter.TopicConverter;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;
import ru.tsu.hits.forum.Users.dto.CreateUpdateUserDto;
import ru.tsu.hits.forum.Users.dto.UserDto;
import ru.tsu.hits.forum.Users.dto.UserRoleDto;
import ru.tsu.hits.forum.Users.dto.converter.UserConverter;
import ru.tsu.hits.forum.Users.repository.UserFindRepository;
import ru.tsu.hits.forum.Users.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tsu.hits.forum.Topic.dto.converter.TopicConverter.topicToDto;
import static ru.tsu.hits.forum.Users.dto.converter.UserConverter.userToDto;
import static ru.tsu.hits.forum.Users.dto.converter.UserConverter.userToEntity;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFindRepository userFindRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

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
        var entity = userToEntity(createUpdateUserDto);
        var savedEntity = userRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(userToDto(savedEntity));
    }

    @Transactional(readOnly = true)
    public Boolean checkRole(String id, UserRoleDto roleRequired){
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        int i = roleRequired.hashCode();
        return userEntity.getRole().hashCode() >= i;
    }

    @Transactional
    public UserDto giveRole(String id, String userId, UserRoleDto roleForUser){
        var giverEntity = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        int giverRole = giverEntity.getRole().hashCode();
        int userRole = userEntity.getRole().hashCode();

        if(roleForUser.hashCode() < giverRole && userRole< giverRole){
            userEntity.setRole(roleForUser);
            var savedEntity = userRepository.save(userEntity);
            return userToDto(savedEntity);
        }
        else return userToDto(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(){
        return userFindRepository.findAll().stream()
                .map(UserConverter:: userToDto)
                .collect(Collectors.toList());
    }


    //система через логин/id, поэтому будет работать неправильно при одинаковых login
    //даже так не сделал, надо прописать какой-то чекер в каждом из сервисов иначе не билдятся бины правильно
    @Transactional(readOnly = true)
    public Boolean checkTopicAuthor(String userId, String topicID){
        var userEntity = userRepository.findById(userId)
        .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        var topicEntity = topicRepository.findById(topicID)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        return userEntity.getLogin() == topicEntity.getAuthor();
    }

    @Transactional(readOnly = true)
    public Boolean checkMessageAuthor(String userId, String messageID){
        var userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        var messageEntity = messageRepository.findById(messageID)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        return userEntity.getLogin() == messageEntity.getAuthor();
    }
}
