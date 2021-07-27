package ru.pet.lunchvote.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pet.lunchvote.Security;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Value("${welcome.message}")
    String message;

    UserRepository repository;

    public RootController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @GetMapping("/")
    public String main(Model model, HttpServletRequest request){
        log.info("Main");
        Cookie cookie = List.of(request.getCookies()).stream().filter(c -> "session".equals(c.getName())).findFirst().get();
        int id = cookie == null? -1: Security.checkLogin(cookie.getValue());
        log.info("Cookie: " + cookie.getValue());
        if (id > 0) {
            log.info("Has logged user with id " + id);
            model.addAttribute("user", repository.getById(id));
        }
        return "index";
    }

    @PostMapping("/login")
    public String login(String email, String pass, HttpServletRequest request, HttpServletResponse response){
       log.info("login with: " + email + ", password: " + pass);
       if (email == null || pass == null) return "index";

       User user = repository.getByEmail(email);
       if (user != null && user.getPassword().equals(pass) && user.getEnabled()) {
           String uuid = Security.login(user.getId());
           Cookie cookie = new Cookie("session", uuid);
           response.addCookie(cookie);
           log.info("Login successful.");
           request.setAttribute("user", user);
       }
       else log.error("Login failed! ");

       return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
       List<Cookie> cookies = List.of(request.getCookies());
       Cookie cookie = cookies.stream().filter(c -> "session".equals(c.getName())).findFirst().get();
       Security.logout(cookie.getValue());
       cookie.setMaxAge(0);
       response.addCookie(cookie);

       return "index";
    }

}
