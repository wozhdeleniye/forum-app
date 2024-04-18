package ru.tsu.hits.forum.Category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.common.dto.categoryDto.CategoryDto;
import ru.tsu.hits.common.dto.categoryDto.CreateUpdateCategoryDto;
import ru.tsu.hits.common.dto.categoryDto.HierarchyDto;
import ru.tsu.hits.forum.Category.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forum/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('')")
    public CategoryDto create(@RequestBody CreateUpdateCategoryDto createUpdateCategoryDto){
        return categoryService.create(createUpdateCategoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto get(@PathVariable UUID id){
        return categoryService.getById(id.toString());
    }


    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable UUID id, @RequestBody CreateUpdateCategoryDto createUpdateCategoryDto){
        return categoryService.edit(id.toString(), createUpdateCategoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        categoryService.delete(id.toString());
    }

    @GetMapping("/hierarchy")
    public List<HierarchyDto> getHierarchy(){
        return categoryService.getHierarchy();
    }
}
