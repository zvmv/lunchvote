package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="restaurant")
@Getter
@Setter
public class Restaurant extends AbstractBaseEntity {
    @NotBlank
    @Max(15)
    private String name;

    @Max(50)
    private String address;
}
