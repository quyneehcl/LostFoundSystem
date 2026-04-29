package com.lostfound.demo.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Initializes Firebase connection on application startup.
 * Reads serviceAccountKey.json from resources folder.
 * @author Nguyen Minh Quyen
 */
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount =
                    getClass().getClassLoader()
                              .getResourceAsStream("serviceAccountKey.json");

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
}