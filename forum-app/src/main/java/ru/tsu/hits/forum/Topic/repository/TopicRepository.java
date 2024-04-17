package ru.tsu.hits.forum.Topic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<TopicEntity, String> {

    @Query("select t from TopicEntity t where t.name LIKE %:name%")
    List<TopicEntity> findAllByNameIgnoreCase(@Param("name") String name);

    List<TopicEntity> findAllByParentCategory(String id);
}