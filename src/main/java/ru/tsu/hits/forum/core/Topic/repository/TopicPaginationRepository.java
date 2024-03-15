package ru.tsu.hits.forum.core.Topic.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.forum.core.Category.entity.CategoryEntity;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;

import java.util.Date;
import java.util.List;

public interface TopicPaginationRepository extends JpaRepository<TopicEntity, String> {

}
