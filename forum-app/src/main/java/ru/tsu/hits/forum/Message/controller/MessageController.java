package ru.tsu.hits.forum.Message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.messageDto.CreateUpdateMessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageDto;
import ru.tsu.hits.common.dto.messageDto.MessageSearchDto;
import ru.tsu.hits.common.dto.messageDto.MessageSearchResponseDto;
import ru.tsu.hits.common.security.exception.ForbiddenException;
import ru.tsu.hits.forum.Message.converter.MessageConverter;
import ru.tsu.hits.forum.Message.entity.MessageEntity;
import ru.tsu.hits.forum.Message.service.MessageService;
import ru.tsu.hits.common.dto.topicDto.TopicPagedDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/forum/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public MessageDto create(@RequestBody CreateUpdateMessageDto createUpdateMessageDto){
        return messageService.create(createUpdateMessageDto);
    }

    @GetMapping("get/{id}")
    public MessageDto get(@PathVariable UUID id){
        return messageService.getById(id.toString());
    }

    @PutMapping("/{id}")
    public MessageDto update(@PathVariable UUID id, @RequestBody CreateUpdateMessageDto createUpdateMessageDto, Authentication auth) throws ForbiddenException {
        return messageService.edit(id.toString(), createUpdateMessageDto, auth);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Authentication auth) throws ForbiddenException{
        messageService.delete(id.toString(), auth);
    }

    @GetMapping("get/paged")
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

    @GetMapping("get/text")
    public List<MessageSearchResponseDto> getMessagedByParams(
            @RequestParam(required = false, defaultValue = "") String cat,
            @RequestParam(required = false, defaultValue = "") String top,
            @RequestBody MessageSearchDto dto
            ){
        return messageService.getSortedMessages(cat, top, dto);
    }
}
