package ru.tsu.hits.forum.core.Users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.core.Users.entity.UserEntity;

public interface UserFindRepository extends JpaRepository<UserEntity, String> {
}
