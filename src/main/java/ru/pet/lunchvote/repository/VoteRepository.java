package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
