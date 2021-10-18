package ru.pet.lunchvote.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class User extends AbstractBaseEntity implements UserDetails {

    public User(){
    }

    public User(String email, String password, String name, Boolean enabled, Boolean admin) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enabled = enabled;
        this.admin = admin;
    }

    public User(Integer id, String email, String password, String name, Boolean enabled, Boolean admin) {
        this(email, password, name, enabled, admin);
        this.setId(id);
    }

    @Column(name="email", length = 30, nullable = false, unique = true)
    @NotBlank
    @Size( max = 30)
    @Email
    private String email;

    @Column(name="password", length = 16, nullable = false)
    @NotNull
    @Size( min = 4, max = 16)
    private String password;

    @Column(name="name", length = 30, nullable = false)
    @NotBlank
    @Size( max = 30)
    private String name;

    @Column(name="enabled", nullable = false)
    @NotNull
    private Boolean enabled;

    @Column(name="admin", nullable = false)
    @NotNull
    private Boolean admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAdmin()
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
        return getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getEnabled();
    }
}
