package com.example.AddressVerifier.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, KeyAuthenticationManager keyAuthenticationManager, KeyAuthenticationConverter keyAuthenticationConverter){

        // Create the authentication filter
        final AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(keyAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(keyAuthenticationConverter);


        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // if you don't need CSRF
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable) // Disable default HTTP Basic
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable) // Disable default form login
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/home").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
