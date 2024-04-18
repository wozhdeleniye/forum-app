package ru.tsu.hits.user.Users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.user.Users.entity.UserEntity;

import java.util.List;

@Repository
public interface UserFindRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findFirstByLogin(String login);
}
