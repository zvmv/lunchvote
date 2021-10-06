package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User extends AbstractBaseEntity implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return isAdmin()
                ? List.of(
                (GrantedAuthority) () -> "ROLE_ADMIN",
                (GrantedAuthority) () -> "ROLE_USER")
                : Collections.singleton((GrantedAuthority) () -> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }
}
