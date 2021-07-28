package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
