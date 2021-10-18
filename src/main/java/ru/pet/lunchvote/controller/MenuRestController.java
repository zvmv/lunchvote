package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.repository.MenuRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/menus")
@Transactional
public class MenuRestController {
    private MenuRepository repository;
    private static final Logger log = LoggerFactory.getLogger(MenuRestController.class);
    public static final String REST_URL = "/menus";

    public MenuRestController(MenuRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    };

    @GetMapping("/today")
    public ResponseEntity<List<Menu>> getAllToday() {
        return ResponseEntity.ok(repository.getAllByMenudate(LocalDate.now()));
    };

    @GetMapping("/date/{dateVariable}")
    public ResponseEntity<List<Menu>> getByDate(@PathVariable String dateVariable) {
        LocalDate date = LocalDate.parse(dateVariable);
        log.info("Get menus by date + ", date);
        return ResponseEntity.ok(repository.getAllByMenudate(date));
    };

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getById(@PathVariable int id){
        try { return ResponseEntity.ok(repository.findById(id).get()); }
        catch (NoSuchElementException e) { return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable int id){
        try { repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Menu> create(@RequestBody Menu body) {
        if (body.getId() != null) return ResponseEntity.badRequest().build();
        Menu created = repository.save(body);
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(created);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Menu> update(@RequestBody Menu body, @PathVariable Integer id) {
        if (id.equals(body.getId()) == false) return ResponseEntity.badRequest().build();
        repository.save(body);
        return ResponseEntity.ok(body);
    }

}
