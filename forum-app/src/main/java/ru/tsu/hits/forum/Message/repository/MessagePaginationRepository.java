package ru.tsu.hits.forum.Message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Message.entity.MessageEntity;

@Repository
public interface MessagePaginationRepository extends JpaRepository<MessageEntity, String> {
}
