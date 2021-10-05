package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteTO {
    Integer id;
    String restaurant;
    String userName;
    Integer menuId;
}
