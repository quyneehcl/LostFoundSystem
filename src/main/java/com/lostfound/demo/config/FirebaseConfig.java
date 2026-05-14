package com.lostfound.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {

                String json = System.getenv("FIREBASE_CONFIG");

                if (json == null || json.isEmpty()) {
                    throw new RuntimeException("FIREBASE_CONFIG is missing");
                }

                InputStream stream =
                    new ByteArrayInputStream(json.replace("\\n", "\n").getBytes());

                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .setDatabaseUrl("https://vinuni-lostfou-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .build();

                FirebaseApp.initializeApp(options);

                System.out.println("Firebase initialized successfully from ENV");
            }
        } catch (Exception e) {
            System.err.println("Firebase initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
