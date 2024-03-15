package ru.tsu.hits.forum.core.Category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.forum.core.Category.dto.CategoryDto;
import ru.tsu.hits.forum.core.Category.dto.CreateUpdateCategoryDto;
import ru.tsu.hits.forum.core.Category.dto.HierarchyDto;
import ru.tsu.hits.forum.core.Category.service.CategoryService;
import ru.tsu.hits.forum.core.Users.dto.UserId;
import ru.tsu.hits.forum.core.Users.dto.UserRoleDto;
import ru.tsu.hits.forum.core.Users.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
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
