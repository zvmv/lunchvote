package ru.pet.lunchvote.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.pet.lunchvote.model.Restaurant;
import ru.pet.lunchvote.repository.RestaurantRepository;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@Transactional
public class RestaurantRestController extends GenericRestController<Restaurant>{

    public RestaurantRestController(RestaurantRepository repository) {
        super(repository);
    }
}
