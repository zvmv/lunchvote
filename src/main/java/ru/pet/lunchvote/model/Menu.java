package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    LocalDate menudate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    Restaurant restaurant;

    @NotNull
    String dishes;

    Integer price;
}
