package ru.pet.lunchvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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
    public List<User> getAll(){
      return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        return new ResponseEntity<>(repository.getById(id),HttpStatus.OK);
    }

    @PutMapping("users")
    public ResponseEntity<User> put(@RequestBody User user){
        repository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(null);
    }
}
