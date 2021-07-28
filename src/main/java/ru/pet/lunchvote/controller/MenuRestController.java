package ru.pet.lunchvote.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.pet.lunchvote.model.Menu;

@RestController("restaurant/menu")
public class MenuRestController extends GenericRestController<Menu> {
}
