package ru.tsu.hits.forum.Search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.forum.Search.dto.SearchByNameDto;
import ru.tsu.hits.forum.Search.dto.SearchDataDto;
import ru.tsu.hits.forum.Search.service.SearchService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/ctm")
    public List<SearchDataDto> create(
            @RequestParam(required = false, defaultValue = "true") boolean cat,
            @RequestParam(required = false, defaultValue = "true") boolean top,
            @RequestParam(required = false, defaultValue = "true") boolean mes,
            @RequestBody SearchByNameDto dto
            ){
        List<SearchDataDto> responseList = Collections.emptyList();
        if(cat){
            responseList = searchService.getCategories(dto.getText());
        }
        if(top){
            responseList = Stream.concat(responseList.stream(), searchService.getTopics(dto.getText()).stream()).toList();
        }
        if(mes){
            responseList = Stream.concat(responseList.stream(), searchService.getMessages(dto.getText()).stream()).toList();
        }

        return responseList;
    }
}
