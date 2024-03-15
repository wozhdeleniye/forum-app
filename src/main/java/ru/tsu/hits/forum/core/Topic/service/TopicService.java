package ru.tsu.hits.forum.core.Topic.service;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.forum.core.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.core.Exception.dto.StatusCode;
import ru.tsu.hits.forum.core.Message.repository.MessageRepository;
import ru.tsu.hits.forum.core.Topic.dto.CreateUpdateTopicDto;
import ru.tsu.hits.forum.core.Topic.dto.TopicDto;
import ru.tsu.hits.forum.core.Topic.dto.TopicPagedDto;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.core.Topic.repository.TopicPaginationRepository;
import ru.tsu.hits.forum.core.Topic.repository.TopicRepository;

import java.net.BindException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static ru.tsu.hits.forum.core.Topic.dto.converter.TopicConverter.topicToDto;
import static ru.tsu.hits.forum.core.Topic.dto.converter.TopicConverter.topicToEntity;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final CategoryRepository categoryRepository;
    private final TopicPaginationRepository topicPaginationRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public ResponseEntity<?> create(CreateUpdateTopicDto createUpdateTopicDto){
        var categoryEntity = categoryRepository.findById(createUpdateTopicDto.getParentCategory())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        if(categoryEntity.getChildCount() == 0){
            var entity = topicToEntity(createUpdateTopicDto);
            var savedEntity = topicRepository.save(entity);
            return ResponseEntity
                    .status(200)
                    .body(topicToDto(savedEntity));
        }
        return ResponseEntity
                .status(400)
                .body(new StatusCode(
                        "Категория не является конечной",
                        400
                ) );
    }

    @Transactional(readOnly = true)
    public TopicDto getById(String id){
        var entity  = topicRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        return topicToDto(entity);
    }

    @Transactional
    public ResponseEntity<?> edit(String id, CreateUpdateTopicDto createUpdateTopicDto){
        var entity  = topicRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        entity.setName(createUpdateTopicDto.getName());
        var categoryEntity = categoryRepository.findById(createUpdateTopicDto.getParentCategory())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        if(categoryEntity.getChildCount() >= 0){
            return ResponseEntity
                    .status(400)
                    .body(new StatusCode(
                            "Категория не является конечной",
                            400
                    ) );
        }
        entity.setParentCategory(createUpdateTopicDto.getParentCategory());
        entity.setLastModDate(new Date(System.currentTimeMillis()));

        var savedEntity = topicRepository.save(entity);
        return ResponseEntity
                .status(200)
                .body(topicToDto(savedEntity));
    }

    @Transactional
    public void delete(String id){
        var entity  = topicRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        topicRepository.delete(entity);
        var messageEntity = messageRepository.findAllByTopic(UUID.fromString(id));
        messageRepository.deleteAll(messageEntity);
    }

    @Transactional(readOnly = true)
    public Page<TopicEntity> topicPagedList(PageRequest pageRequest){
        return topicPaginationRepository.findAll(pageRequest);
    }
}
