package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.lunchvote.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
