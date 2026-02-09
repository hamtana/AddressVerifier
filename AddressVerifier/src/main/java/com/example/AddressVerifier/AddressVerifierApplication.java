package com.example.AddressVerifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {
	SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@EnableCaching
public class AddressVerifierApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressVerifierApplication.class, args);
	}

}
