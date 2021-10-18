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

    public Menu(){
    }

    public Menu(Integer id, LocalDate menudate, String restaurant, String dishes, Integer price) {
        this(menudate, restaurant, dishes, price);
        this.setId(id);
    }

    public Menu(LocalDate menudate, String restaurant, String dishes, Integer price) {
        this.menudate = menudate;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.price = price;
    }

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
