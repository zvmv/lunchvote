package ru.pet.lunchvote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Value("${welcome.message}")
    String message;

    @GetMapping({"/","/index"})
    public String main(Model model){
        log.info("Main");
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/hello")
    public String hello(){
        log.info("Hello in log");
        return "hello";
    }
}
