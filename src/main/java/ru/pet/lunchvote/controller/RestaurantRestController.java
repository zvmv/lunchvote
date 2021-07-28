package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.Restaurant;
import ru.pet.lunchvote.repository.RestaurantRepository;

import java.util.List;

@RestController
public class RestaurantRestController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    RestaurantRepository repository;

    public RestaurantRestController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Restaurant> getById(@PathVariable("id") Integer id){
        try { return ResponseEntity.ok(repository.getById(id)); }
        catch (EmptyResultDataAccessException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> put(@RequestBody Restaurant restaurant){
        repository.save(restaurant);
        return ResponseEntity.ok(restaurant);
    }
}
