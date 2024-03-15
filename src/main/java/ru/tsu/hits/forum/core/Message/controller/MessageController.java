package ru.tsu.hits.forum.core.Message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.forum.core.Message.dto.CreateUpdateMessageDto;
import ru.tsu.hits.forum.core.Message.dto.MessageDto;
import ru.tsu.hits.forum.core.Message.dto.MessageSearchDto;
import ru.tsu.hits.forum.core.Message.dto.MessageSearchResponseDto;
import ru.tsu.hits.forum.core.Message.dto.converter.MessageConverter;
import ru.tsu.hits.forum.core.Message.entity.MessageEntity;
import ru.tsu.hits.forum.core.Message.service.MessageService;
import ru.tsu.hits.forum.core.Topic.dto.TopicPagedDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageDto create(@RequestBody CreateUpdateMessageDto createUpdateMessageDto){
        return messageService.create(createUpdateMessageDto);
    }

    @GetMapping("/{id}")
    public MessageDto get(@PathVariable UUID id){
        return messageService.getById(id.toString());
    }

    @PutMapping("/{id}")
    public MessageDto update(@PathVariable UUID id, @RequestBody CreateUpdateMessageDto createUpdateMessageDto){
        return messageService.edit(id.toString(), createUpdateMessageDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        messageService.delete(id.toString());
    }

    @GetMapping("/paged")
    public TopicPagedDto<MessageDto> getMessages(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ){
        Page<MessageEntity> pagedData = messageService.messagePagedList(PageRequest.of(page, size));

        TopicPagedDto<MessageDto> pagedDataDto = new TopicPagedDto<>();
        pagedDataDto.setData(pagedData.getContent()
                .stream()
                .map(MessageConverter:: messageToDto)
                .collect(Collectors.toList()));
        pagedDataDto.setTotal(pagedData.getTotalElements());
        pagedDataDto.setPages(pagedData.getTotalPages());
        pagedDataDto.setSize(pagedData.getSize());
        return pagedDataDto;
    }

    @GetMapping("/text")
    public List<MessageSearchResponseDto> getMessagedByParams(
            @RequestParam(required = false, defaultValue = "") String cat,
            @RequestParam(required = false, defaultValue = "") String top,
            @RequestBody MessageSearchDto dto
            ){
        return messageService.getSortedMessages(cat, top, dto);
    }
}
