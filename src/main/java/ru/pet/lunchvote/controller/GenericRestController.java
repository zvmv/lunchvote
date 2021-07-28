package ru.pet.lunchvote.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.Restaurant;
import ru.pet.lunchvote.repository.RestaurantRepository;

import java.util.List;

abstract public class GenericRestController<T> {
    GenericRepository<T> repository;

    public GenericRestController(GenericRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable("id") Integer id){
        try { return ResponseEntity.ok(repository.getById(id)); }
        catch (EmptyResultDataAccessException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Restaurant> put(@RequestBody Restaurant restaurant){
        repository.save(restaurant);
        return ResponseEntity.ok(restaurant);
}
