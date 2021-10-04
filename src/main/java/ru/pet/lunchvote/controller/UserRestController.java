package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
@Transactional
public class UserRestController {
    UserRepository repository;
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    public static final String REST_URL = "/users";

    public UserRestController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable int id){
        try { return ResponseEntity.ok(repository.findById(id).get()); }
        catch (NoSuchElementException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User body) {
        if (body.getId() != null) return ResponseEntity.badRequest().build();
        User created = repository.save(body);
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User body, @PathVariable Integer id) {
        if (id.equals(body.getId()) == false) return ResponseEntity.badRequest().build();
        repository.save(body);
        return ResponseEntity.ok(body);
    }
}
