package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User getByEmail(String email);

    @Modifying
    @Query("update User u set u.email = :#{#user.email}, " +
            "u.name = :#{#user.name}, " +
            "u.password = :#{#user.password}, " +
            "u.admin = :#{#user.admin}, " +
            "u.enabled = :#{#user.enabled} where u.id = :#{#user.id}")
    public int update(@Param("user") User user);
}
