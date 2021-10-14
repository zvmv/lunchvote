package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends AbstractBaseEntity {
    @NotNull
    @Column(nullable = false)
    private LocalDate menudate;

    @NotNull
    @Size( max = 16 )
    @Column(nullable = false)
    private String restaurant;

    @NotNull
    @Size( max = 255 )
    @Column(nullable = false)
    private String dishes;

    @NotNull
    @Column(nullable = false)
    private Integer price;
}
