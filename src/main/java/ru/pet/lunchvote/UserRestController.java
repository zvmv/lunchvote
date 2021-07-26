package ru.pet.lunchvote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class UserRestController {
    private UserRepository repository;
    private EntityManager em;

    public UserRestController(UserRepository repository, EntityManager em) {
        this.repository = repository;
        this.em = em;
    }

    @GetMapping("/users")
    public List<User> getAll(){
      return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(repository.getById(id));
    }

    @PutMapping("users")
    public ResponseEntity<User> insertNew(@RequestBody User user){
        repository.save(user);
        return ResponseEntity.ok(user);
    }
}
