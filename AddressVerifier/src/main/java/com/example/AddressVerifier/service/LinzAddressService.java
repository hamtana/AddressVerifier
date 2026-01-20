package com.example.AddressVerifier.service;

import com.example.AddressVerifier.client.LinzWfsClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LinzAddressService {

    // Service class creates an isntance of the client, and then creates a function which converts the inputted string into a request.

    private final LinzWfsClient client;

    public LinzAddressService(LinzWfsClient client){
        this.client = client;
    }

    /**
     * Send the user entered material from the controller into the client
     * @param address
     * @return
     */
    public Mono<String> verifyAddress(String address) {

        // Prevent SQL injection attacks
        String safeAddress = address.replace("'", "''");

        String cql = String.format("full_address ILIKE '%s%%'", safeAddress);

        return client.queryByCql(cql);
    }


}
