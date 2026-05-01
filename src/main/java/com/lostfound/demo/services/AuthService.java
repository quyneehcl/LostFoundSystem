package com.lostfound.demo.services;

import java.util.HashMap;
import java.util.UUID;

import com.lostfound.demo.models.User;

/**
 * Manages user authentication and registration.
 * @author Phan Minh Duc
 */

public class AuthService {
    private HashMap<String, User> userMap = new HashMap<>();

    public AuthService() {
        // Pre-load an Admin account
        User admin = new User(UUID.randomUUID().toString(), 
            "Admin",
            "admin@vinuni.edu.vn", 
            "admin"
        );
        userMap.put(admin.getEmail(), admin);
    }

    // Login or autoregister a user by email.
    public User login(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        // Check if user already exists
        if (userMap.containsKey(email)) {
            return userMap.get(email);
        }

        // Create new user with "user" role
        else {
            String id = UUID.randomUUID().toString();
            String name = email.split("@")[0]; // Use part before @ as name
            String role = "user";
            User newUser = new User(id, name, email, role);
            userMap.put(email, newUser);
            return newUser;
        }
    }

    // Get a user by email
    public User getUserByEmail(String email) {
        return userMap.get(email);
    }
}
