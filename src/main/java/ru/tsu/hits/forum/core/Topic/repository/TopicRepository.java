package ru.tsu.hits.forum.core.Topic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.forum.core.Category.entity.CategoryEntity;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;

import java.awt.print.Pageable;
import java.util.List;

public interface TopicRepository extends CrudRepository<TopicEntity, String> {

    @Query("select t from TopicEntity t where t.name LIKE %:name%")
    List<TopicEntity> findAllByNameIgnoreCase(@Param("name") String name);

    List<TopicEntity> findAllByParentCategory(String id);
}