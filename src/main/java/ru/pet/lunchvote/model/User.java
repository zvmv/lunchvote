package ru.pet.lunchvote.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="users")
@Getter
@Setter
public class User extends AbstractBaseEntity {
    @Column(name="email", length = 30, nullable = false, unique = true)
    @NotBlank
    @Max(30)
    @Email
    private String email;

    @Column(name="password", length = 16, nullable = false)
    @NotNull
    @Max(16)
    @Min(5)
    private String password;

    @Column(name="name", length = 30, nullable = false)
    @NotBlank
    @Max(30)
    private String name;

    @Column(name="enabled")
    @NotNull
    private boolean enabled;

    @Column(name="admin")
    @NotNull
    private boolean admin;

}
