package com.lostfound.demo.services;

import java.util.HashMap;
import java.util.UUID;

import com.lostfound.demo.models.User;

public class AuthService {
    private HashMap<String, User> userMap = new HashMap<>();
     public AuthService() {
        User admin = new User(UUID.randomUUID().toString(), "Admin",
         "admin@vinuni.edu.vn", "admin");
        userMap.put(admin.getEmail(), admin);
    }

    public User login(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        if (userMap.containsKey(email)) {
            return userMap.get(email);
        }

        else {
            String id = UUID.randomUUID().toString();
            String name = email.split("@")[0]; 
            String role = "user";
            User newUser = new User(id, name, email, role);
            userMap.put(email, newUser);
            return newUser;
        }
    }

    public User getUserByEmail(String email) {
        return userMap.get(email);
    }
}
