package ru.tsu.hits.forum.Category.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

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

    @Column(name = "parent_category", nullable = true)
    private String parentCategory;

    @Column(name = "child_count")
    @ColumnDefault("0")
    private Integer childCount;
}
