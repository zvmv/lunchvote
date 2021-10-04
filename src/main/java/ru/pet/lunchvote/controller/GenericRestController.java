package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.model.AbstractBaseEntity;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

abstract public class GenericRestController<T extends AbstractBaseEntity> {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    JpaRepository<T, Integer> repository;
    public String REST_URL = null;

    public GenericRestController(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable int id){
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
    public ResponseEntity<T> create(@RequestBody T body) {
        if (body.getId() != null) return ResponseEntity.badRequest().build();
        T created = repository.save(body);
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@RequestBody T body, @PathVariable Integer id) {
        if (id.equals(body.getId()) == false) return ResponseEntity.badRequest().build();
        repository.save(body);
        return ResponseEntity.ok(body);
    }
}
