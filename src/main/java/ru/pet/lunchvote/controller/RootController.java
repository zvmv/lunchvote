package ru.pet.lunchvote.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.lunchvote.Security;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    UserRepository userRepository;

    public RootController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String pass){
       log.info("login with: " + email + ", password: " + pass);
       if (email == null || pass == null) ResponseEntity.badRequest().build();

       User user = userRepository.getByEmail(email);
       if (user != null && pass.equals(user.getPassword()) && user.isEnabled()) {
           Security.login(user.getId());
           log.info("Login successful.");
           return ResponseEntity.ok().build();
       }
       else log.error("Login failed! ");
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
