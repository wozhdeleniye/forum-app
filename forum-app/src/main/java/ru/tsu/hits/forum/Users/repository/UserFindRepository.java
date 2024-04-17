package ru.tsu.hits.forum.Users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Users.entity.UserEntity;
@Repository
public interface UserFindRepository extends JpaRepository<UserEntity, String> {
}
