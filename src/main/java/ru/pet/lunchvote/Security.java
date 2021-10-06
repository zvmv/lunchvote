package ru.pet.lunchvote;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pet.lunchvote.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Security {
    @Autowired
    private static UserRepository repo;
    private static Integer loggedUserId = null;

    public static void login(Integer id){
       loggedUserId = id;
    }

    public static boolean checkLogin(Integer id){
        return id.equals(loggedUserId);
    }

    public static Integer getLoggedUserId(){
       return loggedUserId;
    }

    public static void logout(){
        loggedUserId = null;
    }

    public static boolean isAdmin(){
       return loggedUserId == null? false: repo.findById(loggedUserId).get().isAdmin();
    }
}
