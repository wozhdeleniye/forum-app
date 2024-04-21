package ru.tsu.hits.forum.Category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.categoryDto.CategoryDto;
import ru.tsu.hits.common.dto.categoryDto.CreateUpdateCategoryDto;
import ru.tsu.hits.common.dto.categoryDto.HierarchyDto;
import ru.tsu.hits.common.security.exception.ForbiddenException;
import ru.tsu.hits.forum.Category.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forum/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto create(@RequestBody CreateUpdateCategoryDto createUpdateCategoryDto, Authentication authentication) throws ForbiddenException {
        return categoryService.create(createUpdateCategoryDto, authentication);
    }

    @GetMapping("get/{id}")
    public CategoryDto get(@PathVariable UUID id){
        return categoryService.getById(id.toString());
    }


    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable UUID id, @RequestBody CreateUpdateCategoryDto createUpdateCategoryDto,Authentication authentication) throws ForbiddenException{
        return categoryService.edit(id.toString(), createUpdateCategoryDto, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Authentication authentication) throws ForbiddenException{
        categoryService.delete(id.toString(), authentication);
    }

    @GetMapping("get/hierarchy")
    public List<HierarchyDto> getHierarchy(){
        return categoryService.getHierarchy();
    }
}
