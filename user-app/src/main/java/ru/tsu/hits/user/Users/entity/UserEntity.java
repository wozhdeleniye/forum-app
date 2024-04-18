package ru.tsu.hits.user.Users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tsu.hits.common.dto.userDto.UserRoleDto;

import java.util.Date;

@Entity
@Table(name = "_user")
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
    private String name;

    @Column
    private String login;

    @Column
    @Enumerated
    private UserRoleDto role;

    @Column(name = "email_adress", nullable = true)
    @Email
    private String email;

    @Column
    private String password;

    @Column(name = "phone_number", nullable = true)
    private String phone;
}
