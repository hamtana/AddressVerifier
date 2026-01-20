package com.example.AddressVerifier.service;

import com.example.AddressVerifier.client.LinzFeatureCollection;
import com.example.AddressVerifier.client.LinzWfsClient;
import com.example.AddressVerifier.model.Address;
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
     * @param addressQuery
     * @return
     */
    public Mono<Address> verifyAddress(String addressQuery) {
        String cql = "full_address ILIKE '" + addressQuery + "%'";

        return client.queryByCql(cql)
                .map(collection -> {
                    if (collection.getFeatures().isEmpty()) return null;

                    var props = collection.getFeatures().get(0).getProperties();
                    return new Address(
                            (String) props.get("full_address"),
                            (String) props.get("suburb_locality"),
                            (String) props.get("town_city")
                    );
                });
    }
}