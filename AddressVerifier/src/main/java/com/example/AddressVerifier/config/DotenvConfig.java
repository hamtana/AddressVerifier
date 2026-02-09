package com.example.AddressVerifier.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    static {
        // Load .env before Spring starts creating beans
        System.out.println("=== Loading .env file (static block) ===");
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            System.out.println("Loading env var: " + entry.getKey());
            System.setProperty(entry.getKey(), entry.getValue());
        });
        System.out.println("=== Finished loading .env ===");
    }
}