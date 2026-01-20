package com.example.AddressVerifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient linzWebClient(

            @Value("${linz.api.base-url}") String baseUrl){

            System.out.println("LINZ URL: " + baseUrl);

            return WebClient.builder()
                    .baseUrl(baseUrl)
                    .build();
    }

}
