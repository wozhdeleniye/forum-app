package ru.tsu.hits.forum.Category.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.tsu.hits.common.dto.categoryDto.CategoryDto;
import ru.tsu.hits.common.dto.categoryDto.CreateUpdateCategoryDto;
import ru.tsu.hits.common.dto.categoryDto.HierarchyDto;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;
import ru.tsu.hits.common.security.JwtUserData;
import ru.tsu.hits.common.security.exception.ForbiddenException;
import ru.tsu.hits.forum.Category.converter.CategoryConverter;
import ru.tsu.hits.forum.Category.entity.CategoryEntity;
import ru.tsu.hits.forum.Category.repository.CategoryRepository;
import ru.tsu.hits.forum.Message.repository.MessageRepository;
import ru.tsu.hits.forum.Topic.repository.TopicRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.tsu.hits.forum.Category.converter.CategoryConverter.categoryToDto;
import static ru.tsu.hits.forum.Category.converter.CategoryConverter.categoryToEntity;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public CategoryDto create(CreateUpdateCategoryDto createUpdateCategoryDto, Authentication auth){
        var user = (JwtUserData)auth.getPrincipal();
        if(!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())){
            throw new ForbiddenException("Вы не админ, чтобы создавать категорию");
        }
        if(createUpdateCategoryDto.getParentCategory() != ""){
            var parentEntity = categoryRepository.findById(createUpdateCategoryDto.getParentCategory())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        }
        var savedEntity = categoryRepository.save(categoryToEntity(createUpdateCategoryDto));

        CategoryEntity parentEntity;
        if(createUpdateCategoryDto.getParentCategory() != ""){
            parentEntity = categoryRepository.findById(createUpdateCategoryDto.getParentCategory())
                    .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
            parentEntity.setChildCount(parentEntity.getChildCount() + 1);
        }

        return categoryToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public CategoryDto getById(String id){
        var entity  = categoryRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        return categoryToDto(entity);
    }

    @Transactional
    public CategoryDto edit(String id, CreateUpdateCategoryDto createUpdateCategoryDto, Authentication auth){
        var user = (JwtUserData)auth.getPrincipal();
        if(!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())){
            throw new ForbiddenException("Вы не админ, чтобы редактировать категорию");
        }
        var entity  = categoryRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));

        entity.setName(createUpdateCategoryDto.getName());
        var categoryEntity  = categoryRepository.findById(createUpdateCategoryDto.getParentCategory())
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        entity.setParentCategory(createUpdateCategoryDto.getParentCategory());
        entity.setLastModDate(new Date(System.currentTimeMillis()));

        var savedEntity = categoryRepository.save(entity);
        return categoryToDto(savedEntity);
    }

    @Transactional
    public void delete(String id, Authentication auth){
        var user = (JwtUserData)auth.getPrincipal();
        if(!user.getRole().equals(UserRoleDto.ADMINISTRATOR.toString())){
            throw new ForbiddenException("Вы не админ, чтобы удалять категорию");
        }
        var entity  = categoryRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
        categoryRepository.delete(entity);
        var topicEntity = topicRepository.findAllByParentCategory(id);
        topicEntity.forEach(child ->{
                    var messageEntity = messageRepository.findAllByTopic(UUID.fromString(id));
                    messageRepository.deleteAll(messageEntity);
                    topicRepository.delete(child);
                }
        );
        var childrenEntity = categoryRepository.findAllByParentCategoryOrderByName(id);
        childrenEntity.forEach(child ->{
            child.setParentCategory("");
            categoryRepository.save(child);
            }
        );
        if(entity.getParentCategory() == ""){
            var parentEntity = categoryRepository.findById(entity.getParentCategory())
                    .orElseThrow(() -> new HttpClientErrorException((HttpStatus.NOT_FOUND)));
            parentEntity.setChildCount(parentEntity.getChildCount() - 1);
        }

    }

    @Transactional(readOnly = true)
    public List<HierarchyDto> getHierarchy(){
        List<HierarchyDto> hierarchy = categoryRepository.findAllByParentCategoryOrderByName("").stream()
                .map(CategoryConverter :: categoryToHierarchy)
                .collect(Collectors.toList());

        return hierarchySearch(hierarchy);
    }
    private List<HierarchyDto> hierarchySearch(List<HierarchyDto> hierarchy){
        for (int i = 0; i < hierarchy.size(); i++) {
            HierarchyDto dto = hierarchy.get(i);
            dto.setHierarchy(categoryRepository.findAllByParentCategoryOrderByName(dto.getId()).stream()
                    .map(CategoryConverter :: categoryToHierarchy)
                    .collect(Collectors.toList()));
            dto.setHierarchy(hierarchySearch(dto.getHierarchy()));
            hierarchy.set(i, dto);
        }
        return hierarchy;
    }
}
