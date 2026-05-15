package com.lostfound.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = resolveCredentials();
                if (serviceAccount == null) {
                    System.err.println("Firebase initialization failed: no credentials found.");
                    return;
                }
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully.");
            }
        } catch (Exception e) {
            System.err.println("Firebase initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private InputStream resolveCredentials() {
        // Prefer env var (set FIREBASE_CREDENTIALS to the JSON content on Railway)
        String json = System.getenv("FIREBASE_CREDENTIALS");
        if (json != null && !json.isBlank()) {
            return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        }
        // Fall back to bundled file for local development
        return getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
    }
}