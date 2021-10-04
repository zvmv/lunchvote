package ru.pet.lunchvote.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
@RequestMapping("/users")
@Transactional
public class UserRestController extends GenericRestController<User> {
    public UserRestController(UserRepository repository) {
        super(repository);
        REST_URL = "/users";
    }
}
