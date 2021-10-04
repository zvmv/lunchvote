package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    @NotNull
    private LocalDate votedate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Menu menu;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
