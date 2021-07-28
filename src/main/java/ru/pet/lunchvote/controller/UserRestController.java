package ru.pet.lunchvote.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class UserRestController {
    private UserRepository repository;

    public UserRestController(UserRepository repository, EntityManager em) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll(){
      return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        try { return ResponseEntity.ok(repository.getById(id)); }
        catch (EmptyResultDataAccessException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/users")
    public ResponseEntity<User> put(@RequestBody User user){
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }
}
