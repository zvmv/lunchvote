package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu extends AbstractBaseEntity {
    @NotNull
    private LocalDate date;

    @NotNull
    @Max(16)
    private String restaurant;

    @NotNull
    @Max(255)
    private String dishes;

    @NotNull
    private Integer price;
}
