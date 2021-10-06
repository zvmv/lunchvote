package ru.pet.lunchvote.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pet.lunchvote.Security;
import ru.pet.lunchvote.repository.UserRepository;

import java.util.UUID;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    UserRepository userRepository;

    public RootController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam UUID session){
        Security.logout(session);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String pass){
        log.info("login with: " + email + ", password: " + pass);
        if (email == null || pass == null) ResponseEntity.badRequest().build();
        UUID uuid = Security.login(email, pass);
        if (uuid == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(uuid);
    }
}
