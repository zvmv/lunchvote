package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAll();
}
