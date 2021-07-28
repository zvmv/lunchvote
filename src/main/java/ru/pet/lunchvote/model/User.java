package ru.pet.lunchvote.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.*;
import java.util.Set;

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

    @Column(name="roles", nullable = false)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_roles", joinColumns = @JoinColumn(name ="user_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name="enabled")
    @NotNull
    Boolean enabled;

    public boolean isAdmin(){
       return roles.contains(Role.ADMIN);
    }
}
