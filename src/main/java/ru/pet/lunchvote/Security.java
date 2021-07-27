package ru.pet.lunchvote;

import ru.pet.lunchvote.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Security {
    private static Map<String, Integer> loggedUsersId = new HashMap<>();

    public static String login(Integer id){
        UUID uuid = UUID.randomUUID();
        loggedUsersId.put(uuid.toString(), id);
        return uuid.toString();
    }

    public static int checkLogin(String uuid){
        Integer id = loggedUsersId.get(uuid);
        return id == null? -1: id;
    }

    public static void logout (String uuid){
        if (checkLogin(uuid) > 0) loggedUsersId.remove(uuid);
    }
}
