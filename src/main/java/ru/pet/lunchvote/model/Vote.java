package ru.pet.lunchvote.model;

import java.time.LocalDate;

public class Vote {
    Integer id;
    LocalDate date;
    Menu menu;
    User user;
}
