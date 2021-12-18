package ru.pet.lunchvote.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoteTO {
    private Integer id;
    private Integer userId;
    private Integer menuId;
    private LocalDate voteDate;

    public VoteTO(Vote vote){
        id = vote.getId();
        userId = vote.getUser().getId();
        menuId = vote.getMenu().getId();
        voteDate = vote.getVotedate();
    }
}
