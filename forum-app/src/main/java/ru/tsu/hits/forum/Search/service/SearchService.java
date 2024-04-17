package ru.tsu.hits.forum.Search.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.forum.Category.dto.CategoryDto;
import ru.tsu.hits.forum.Category.dto.HierarchyDto;
import ru.tsu.hits.forum.Category.dto.converter.CategoryConverter;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.Message.dto.MessageDto;
import ru.tsu.hits.forum.Message.dto.converter.MessageConverter;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.forum.Search.dto.SearchDataDto;
import ru.tsu.hits.forum.Topic.dto.TopicDto;
import ru.tsu.hits.forum.Topic.dto.converter.TopicConverter;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.tsu.hits.forum.Category.dto.converter.CategoryConverter.categoryToDto;


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
