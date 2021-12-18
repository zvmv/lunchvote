package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"votedate", "user_id"}, name = "vote_unique_userid_date")})
public class Vote extends AbstractBaseEntity {

    @NotNull
    @Column(nullable = false)
    private LocalDate votedate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
