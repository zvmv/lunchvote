package ru.pet.lunchvote.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.pet.lunchvote.Security;
import ru.pet.lunchvote.model.Menu;
import ru.pet.lunchvote.model.Vote;
import ru.pet.lunchvote.model.VoteTO;
import ru.pet.lunchvote.repository.MenuRepository;
import ru.pet.lunchvote.repository.UserRepository;
import ru.pet.lunchvote.repository.VoteRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<VoteTO>> getAll(){
       return ResponseEntity.ok(repository.findAll().stream().map(this::voteToVoteTO).collect(Collectors.toList()));
    }

    @GetMapping("/winner")
    public ResponseEntity<Menu> getWinner(){
        List<Vote> votes = repository.getAllByVotedate(LocalDate.now());
        if (votes.isEmpty()) return ResponseEntity.noContent().build();
        Map<Menu, Integer> voteTable = new HashMap<>();
        for (Vote vote: votes) voteTable.merge(vote.getMenu(), 1, Math::addExact);
        return ResponseEntity.ok(voteTable.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        try {repository.deleteById(id); }
        catch (EmptyResultDataAccessException e){ return ResponseEntity.notFound().build(); }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> makeVote(@RequestParam int id){
        if (LocalTime.now().isAfter(LocalTime.parse("11:00:00"))) {
            log.warn("Voted too late");
//            return ResponseEntity.badRequest().build();
        }
        Integer userId = Security.getLoggedUserId();
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Vote vote = repository.getByVotedateAndUser(LocalDate.now(), userRepo.getById(userId));
        if (vote == null) {
            vote = new Vote();
            vote.setVotedate(LocalDate.now());
            vote.setUser(userRepo.getById(userId));
        }
        try {
            Menu votedMenu = menuRepo.getById(id);
            if (!LocalDate.now().equals(votedMenu.getMenudate())) {
                log.error("making vote: menu outdated");
                return ResponseEntity.badRequest().build();
            };
            vote.setMenu(votedMenu);
        } catch (Exception e){
            log.error("making vote: menu does not exists");
            return ResponseEntity.badRequest().build();
        }
        repository.save(vote);
        VoteTO created = voteToVoteTO(vote);
        log.info("user " + vote.getUser().getName() + " make vote for " + vote.getMenu().getRestaurant());
        URI createdURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(createdURI).body(created);
    }

    public VoteTO voteToVoteTO(Vote vote){
        VoteTO to = new VoteTO();
        to.setId(vote.getId());
        to.setUserName(vote.getUser().getName());
        to.setRestaurant(vote.getMenu().getRestaurant());
        to.setMenuId(vote.getMenu().getId());
        return to;
    }

}
