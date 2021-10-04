package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.Security;
import ru.pet.lunchvote.model.Vote;
import ru.pet.lunchvote.repository.MenuRepository;
import ru.pet.lunchvote.repository.UserRepository;
import ru.pet.lunchvote.repository.VoteRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/votes")
@Transactional
public class VoteRestController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    private VoteRepository repository;
    private UserRepository userRepo;
    private MenuRepository menuRepo;
    public static final String REST_URL = "/votes";

    public VoteRestController(VoteRepository repository, UserRepository userRepo, MenuRepository menuRepo) {
        this.repository = repository;
        this.userRepo = userRepo;
        this.menuRepo = menuRepo;
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAll(){
       return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Vote> makeVote(@RequestParam int id){
        Integer userId = Security.getLoggedUserId();
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Vote vote = new Vote();
        vote.setVotedate(LocalDate.now());
        vote.setUser(userRepo.getById(userId));
        try {
            vote.setMenu(menuRepo.getById(id));
        } catch (Exception e){
            log.error("making vote: menu does not exists");
            return ResponseEntity.badRequest().build();
        }
        Vote created = repository.save(vote);
        log.info("user " + vote.getUser().getName() + " make vote for " + vote.getMenu().getRestaurant());
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(vote);
    }

}
