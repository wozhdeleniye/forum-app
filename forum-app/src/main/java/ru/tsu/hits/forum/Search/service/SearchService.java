package ru.tsu.hits.forum.Search.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.forum.Category.converter.CategoryConverter;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.Message.converter.MessageConverter;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.common.dto.searchDto.SearchDataDto;
import ru.tsu.hits.forum.Topic.converter.TopicConverter;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SearchService {

    private final CategoryRepository categoryRepository;
    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<SearchDataDto> getCategories(String name){
        return categoryRepository.findAllByNameIgnoreCase(name).stream()
                .map(CategoryConverter:: categoryToSearchDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SearchDataDto> getMessages(String text){
        return messageRepository.findAllByTextIgnoreCase(text).stream()
                .map(MessageConverter:: messageToSearchDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SearchDataDto> getTopics(String name){
        return topicRepository.findAllByNameIgnoreCase(name).stream()
                .map(TopicConverter:: topicToSearchDto)
                .collect(Collectors.toList());
    }


}
