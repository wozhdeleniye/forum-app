package ru.tsu.hits.forum.Message.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.forum.Message.entity.MessageEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, String> {
    @Query("select m from MessageEntity m where m.text LIKE %:text%")
    List<MessageEntity> findAllByTextIgnoreCase(@Param("text") String text);

    @Query("select m from MessageEntity m where m.text LIKE %:text% and m.creationDate >= :from and m.creationDate <= :to and m.author = :author and m.topic = :topic and m.category = :category")
    List<MessageEntity> findAllByAll(String text, Date from, Date to, String author, String topic, String category);

    List<MessageEntity> findAllByTopic(UUID id);
}
