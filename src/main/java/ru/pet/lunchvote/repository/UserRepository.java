package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getByEmail(String email);
}
