package ru.tsu.hits.forum.core.Users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tsu.hits.forum.core.Topic.entity.TopicEntity;
import ru.tsu.hits.forum.core.Users.entity.UserEntity;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    List<UserEntity> findByLogin(String name);
}