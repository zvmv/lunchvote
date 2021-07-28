package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.AbstractBaseEntity;

import java.util.List;

abstract public class GenericRestController<T extends AbstractBaseEntity> {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    JpaRepository<T, Integer> repository;

    public GenericRestController(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable("id") Integer id){
        try { return ResponseEntity.ok(repository.findById(id).get()); }
        catch (EmptyResultDataAccessException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<T> put(@RequestBody T body) {
        repository.save(body);
        return ResponseEntity.ok(body);
    }
}
