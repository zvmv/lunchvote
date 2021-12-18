package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    public Vote getByVotedateAndUser(LocalDate date, User user);
    public List<Vote> findAllByVotedate(LocalDate date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.menu WHERE v.votedate = ?1")
    public List<Vote> loadAllByVotedate(LocalDate date);


}
