package ru.tsu.hits.forum.Topic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.topicDto.CreateUpdateTopicDto;
import ru.tsu.hits.common.dto.topicDto.TopicDto;
import ru.tsu.hits.common.dto.topicDto.TopicPagedDto;
import ru.tsu.hits.forum.Topic.converter.TopicConverter;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.Topic.service.TopicService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUpdateTopicDto createUpdateTopicDto){
        return topicService.create(createUpdateTopicDto);
    }

    @GetMapping("/{id}")
    public TopicDto get(@PathVariable UUID id){
        return topicService.getById(id.toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody CreateUpdateTopicDto createUpdateTopicDto){
        return topicService.edit(id.toString(), createUpdateTopicDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        topicService.delete(id.toString());
    }

    @GetMapping("/paged")
    public TopicPagedDto<TopicDto> getTopics(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        Page<TopicEntity> pagedData = topicService.topicPagedList(PageRequest.of(page, size));

        TopicPagedDto<TopicDto> pagedDataDto = new TopicPagedDto<>();
        pagedDataDto.setData(pagedData.getContent()
                .stream()
                .map(TopicConverter:: topicToDto)
                .collect(Collectors.toList()));
        pagedDataDto.setTotal(pagedData.getTotalElements());
        pagedDataDto.setPages(pagedData.getTotalPages());
        pagedDataDto.setSize(pagedData.getSize());
        return pagedDataDto;
    }
}