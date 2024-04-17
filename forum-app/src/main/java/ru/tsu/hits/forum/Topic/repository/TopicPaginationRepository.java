package ru.tsu.hits.forum.Topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Topic.entity.TopicEntity;

@Repository
public interface TopicPaginationRepository extends JpaRepository<TopicEntity, String> {

}
