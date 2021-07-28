package ru.pet.lunchvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepostory<T> extends JpaRepository<T, Integer> {
}
