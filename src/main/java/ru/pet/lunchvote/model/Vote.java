package ru.pet.lunchvote.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    @NotNull
    private LocalDate votedate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
