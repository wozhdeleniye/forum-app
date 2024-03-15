package ru.tsu.hits.forum.core.Message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.forum.core.Message.entity.MessageEntity;

public interface MessagePaginationRepository extends JpaRepository<MessageEntity, String> {
}
