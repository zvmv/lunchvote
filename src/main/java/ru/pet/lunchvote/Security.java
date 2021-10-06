package ru.pet.lunchvote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pet.lunchvote.controller.RootController;
import ru.pet.lunchvote.model.User;
import ru.pet.lunchvote.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class Security {
    private static UserRepository repo;
    private static Map<Integer, UUID> loggedUsers = new HashMap<>(5);
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    public Security(UserRepository repo) {
        this.repo = repo;
    }

    public static UUID login(String email, String pass){
        User user = repo.getByEmail(email);
        if (user != null && pass.equals(user.getPassword()) && user.isEnabled()) {
            UUID uuid = UUID.randomUUID();
            loggedUsers.put(user.getId(), uuid);
            log.info("Login successful.");
            return uuid;
        }
        log.error("Login failed!");
        return null;
    }

    public static UUID checkLogin(Integer id){
        return loggedUsers.get(id);
    }

    public static Integer getLoggedUserId(UUID uuid){
        for (Map.Entry<Integer, UUID> entry: loggedUsers.entrySet()) {
            if (uuid.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }

    public static void logout(UUID uuid){
        loggedUsers.remove(uuid);
    }

    public static boolean isAdmin(UUID uuid){
       return repo.getById(getLoggedUserId(uuid)).isAdmin();
    }
}
