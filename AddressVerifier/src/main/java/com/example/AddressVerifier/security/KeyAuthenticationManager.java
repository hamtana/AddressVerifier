package com.example.AddressVerifier.security;

import com.example.AddressVerifier.config.ApiKeyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class KeyAuthenticationManager implements ReactiveAuthenticationManager {
    private static final Logger LOG = LoggerFactory.getLogger(KeyAuthenticationManager.class);

    private final ApiKeyProperties apiKeyProperties;

    public KeyAuthenticationManager(ApiKeyProperties apiKeyProperties) {
        this.apiKeyProperties = apiKeyProperties;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.fromSupplier(() -> {
            if (authentication.getCredentials() == null) {
                throw new BadCredentialsException("Missing API key");
            }

            String apiKey = authentication.getCredentials().toString();

            // Validate against the list of valid API keys
            if (apiKeyProperties.getValidApiKeys().contains(apiKey)) {
                LOG.info("Valid API key authenticated");
                return new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),
                        authentication.getCredentials(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }

            LOG.warn("Invalid API key attempted");
            throw new BadCredentialsException("Invalid API key");
        });
    }
}