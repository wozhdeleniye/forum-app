package ru.tsu.hits.forum.Message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.common.security.JwtUserData;
import ru.tsu.hits.common.security.exception.ForbiddenException;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.common.dto.messageDto.CreateUpdateMessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageSearchDto;
import ru.tsu.hits.common.dto.messageDto.MessageSearchResponseDto;
import ru.tsu.hits.forum.Message.converter.MessageConverter;
import ru.tsu.hits.forum.Message.entity.MessageEntity;
import ru.tsu.hits.forum.Message.repository.MessagePaginationRepository;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tsu.hits.forum.Message.converter.MessageConverter.messageToDto;
import static ru.tsu.hits.forum.Message.converter.MessageConverter.messageToEntity;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final MessagePaginationRepository messagePaginationRepository;

    @Transactional
    public MessageDto create(CreateUpdateMessageDto createUpdateMessageDto){
        var topicEntity = topicRepository.findById(createUpdateMessageDto.getTopic().toString())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        var categoryEntity = categoryRepository.findById(topicEntity.getParentCategory())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        var entity = messageToEntity(createUpdateMessageDto, categoryEntity.getUuid());
        var savedEntity = messageRepository.save(entity);


        return messageToDto(savedEntity);
    }

    @Transactional
    public MessageDto getById(String id){
        var entity  = messageRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        return messageToDto(entity);
    }

    @Transactional
    public MessageDto edit(String id, CreateUpdateMessageDto createUpdateMessageDto, Authentication auth){

        var entity  = messageRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        var user = (JwtUserData)auth.getPrincipal();
        if((!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())) || (!user.getId().toString().equals(entity.getAuthor()))){
            throw new ForbiddenException("У вас нет прав изменять это сообщение");
        }
        entity.setText(createUpdateMessageDto.getText());
        entity.setLastModDate(new Date(System.currentTimeMillis()));

        var savedEntity = messageRepository.save(entity);
        return messageToDto(savedEntity);
    }

    @Transactional
    public void delete(String id, Authentication auth){
        var entity  = messageRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        var user = (JwtUserData)auth.getPrincipal();
        if((!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())) || (!user.getId().toString().equals(entity.getAuthor()))){
            throw new ForbiddenException("У вас нет удалять это сообщение");
        }
        messageRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    public Page<MessageEntity> messagePagedList(PageRequest pageRequest){
        return messagePaginationRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public List<MessageSearchResponseDto> getSortedMessages(String catId, String topId, MessageSearchDto dto){
        return messageRepository.findAllByAll(dto.getText(), dto.getCreationDateFrom(), dto.getCreationDateTo(), dto.getAuthor(), catId, topId).stream()
                .map(MessageConverter:: messageToResponseDto)
                .collect(Collectors.toList());
    }
}
