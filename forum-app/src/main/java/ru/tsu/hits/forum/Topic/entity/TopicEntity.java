package ru.tsu.hits.forum.Topic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "_topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_moderation_date", nullable = true)
    private Date lastModDate;

    @Column
    private String author;

    @Column
    private String name;

    @Column(name = "parent_category")
    private String parentCategory;
}
