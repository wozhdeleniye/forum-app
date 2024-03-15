package ru.tsu.hits.forum.core.Users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tsu.hits.forum.core.Users.dto.UserRoleDto;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id")
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column
    private String login;

    @Column
    @Enumerated
    private UserRoleDto role;
}
