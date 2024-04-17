package ru.tsu.hits.forum.Message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.Message.dto.CreateUpdateMessageDto;
import ru.tsu.hits.forum.Message.dto.MessageDto;
import ru.tsu.hits.forum.Message.dto.MessageSearchDto;
import ru.tsu.hits.forum.Message.dto.MessageSearchResponseDto;
import ru.tsu.hits.forum.Message.dto.converter.MessageConverter;
import ru.tsu.hits.forum.Message.entity.MessageEntity;
import ru.tsu.hits.forum.Message.repository.MessagePaginationRepository;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.forum.Topic.dto.converter.TopicConverter;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.tsu.hits.forum.Message.dto.converter.MessageConverter.messageToDto;
import static ru.tsu.hits.forum.Message.dto.converter.MessageConverter.messageToEntity;

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
    public MessageDto edit(String id, CreateUpdateMessageDto createUpdateMessageDto){
        var entity  = messageRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        entity.setText(createUpdateMessageDto.getText());
        entity.setLastModDate(new Date(System.currentTimeMillis()));

        var savedEntity = messageRepository.save(entity);
        return messageToDto(savedEntity);
    }

    @Transactional
    public void delete(String id){
        var entity  = messageRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
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
