package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteTO {
    Integer id;
    Integer userId;
    Integer menuId;
}
