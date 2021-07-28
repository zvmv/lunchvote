package ru.pet.lunchvote.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.repository.MenuRepository;

@RestController
@RequestMapping("/menu")
@Transactional
public class MenuRestController extends GenericRestController<Menu> {
    public MenuRestController(MenuRepository repository) {
        super(repository);
    }
}
