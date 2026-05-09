package com.lostfound.demo.controllers;

import com.lostfound.demo.models.User;
import com.lostfound.demo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles authentication API endpoint.
 * Receives email from frontend and registers/retrieves user via AuthService.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST /api/auth
     * Receives email from frontend signup, calls AuthService.login() to register or retrieve the user.
     */
    @PostMapping("/auth")
    public ResponseEntity<Map<String, Object>> auth(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            String email = body.get("email");

            // Validate email
            if (email == null || email.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required");
                return ResponseEntity.badRequest().body(response);
            }

            // Login or auto-register via AuthService
            User user = authService.login(email);

            // Build user map for response
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id",    user.getId());
            userMap.put("name",  user.getName());
            userMap.put("email", user.getEmail());
            userMap.put("role",  user.getRole());

            response.put("success", true);
            response.put("user", userMap);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
