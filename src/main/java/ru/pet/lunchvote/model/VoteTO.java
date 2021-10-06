package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteTO {
    private Integer id;
    private Integer userId;
    private Integer menuId;
    public VoteTO(Vote vote){
        id = vote.getId();
        userId = vote.getUser().getId();
        menuId = vote.getMenu().getId();
    }
}
