package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.model.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    public List<Menu> getAllByMenudate(LocalDate date);
    @Modifying
    @Query("update Menu m set m.dishes = :#{#menu.dishes}, " +
            "m.menudate = :#{#menu.menudate}, " +
            "m.price = :#{#menu.price}, " +
            "m.restaurant = :#{#menu.restaurant} where m.id = :#{#menu.id}")
    public int update(@Param("menu") Menu menu);
}
