package ru.tsu.hits.user.Users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.user.Users.entity.UserEntity;

import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    List<UserEntity> findByLogin(String name);
}