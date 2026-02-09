package com.example.AddressVerifier.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ApiKeyProperties {

    private List<String> validApiKeys;
    private String linzApiKey;

    public List<String> getValidApiKeys() {
        return validApiKeys;
    }

    public void setValidApiKeys(List<String> validApiKeys) {
        this.validApiKeys = validApiKeys;
    }

}
