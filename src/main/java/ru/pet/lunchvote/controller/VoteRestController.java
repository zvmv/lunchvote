package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.model.Vote;
import ru.pet.lunchvote.model.VoteTO;
import ru.pet.lunchvote.repository.MenuRepository;
import ru.pet.lunchvote.repository.UserRepository;
import ru.pet.lunchvote.repository.VoteRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/votes")
public class VoteRestController {

    private static final String VOTE_MAX_TIME = "11:00:00";

    private static final Logger log = LoggerFactory.getLogger(VoteRestController.class);
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
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<VoteTO>> getAll(){
       return ResponseEntity.ok(repository.findAll().stream().map(VoteTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/winner/{date}")
    public ResponseEntity<Menu> getWinnerByDate(@PathVariable LocalDate date){
        List<Vote> votes = repository.getAllByVotedate(date);
        if (votes.isEmpty()) return ResponseEntity.noContent().build();
        Map<Menu, Integer> voteTable = new HashMap<>();
        for (Vote vote: votes) voteTable.merge(vote.getMenu(), 1, Math::addExact);
        Menu winner = voteTable.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
        return ResponseEntity.ok(winner);
    }

    @GetMapping("/winner")
    public ResponseEntity<Menu> getWinner(){
        return getWinnerByDate(LocalDate.now());
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable int id){
        try {repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> makeVote(@RequestParam int id){
        if (LocalTime.now().isAfter(LocalTime.parse(VOTE_MAX_TIME))) {
            log.warn("Voted too late");
            return ResponseEntity.badRequest()
                    .body(new ResponseTransfer("You can't change vote after " + VOTE_MAX_TIME, HttpStatus.BAD_REQUEST));
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;

        Vote vote = repository.getByVotedateAndUser(LocalDate.now(), user);
        if (vote == null) {
            vote = new Vote();
            vote.setVotedate(LocalDate.now());
            vote.setUser(user);
        }
        try {
            Menu votedMenu = menuRepo.getById(id);
            vote.setMenu(votedMenu);
        } catch (Exception e){
            log.error("making vote: menu does not exists");
            return ResponseEntity.badRequest().build();
        }
        repository.save(vote);
        VoteTO created = new VoteTO(vote);
        log.info("user " + created.getUserId() + " make vote for " + created.getMenuId());
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(created);
    }
}
