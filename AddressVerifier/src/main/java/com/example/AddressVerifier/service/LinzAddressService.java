package com.example.AddressVerifier.service;

import com.example.AddressVerifier.client.LinzWfsClient;
import com.example.AddressVerifier.exception.AddressNotFoundException;
import com.example.AddressVerifier.model.Address;
import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LinzAddressService {

    private final LinzWfsClient client;
    private final AsyncCache<String, List<Address>> cache;

    public LinzAddressService(LinzWfsClient client) {
        this.client = client;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .buildAsync();
    }

    /**
     * Verify addresses for a query string. Returns a list of matching addresses.
     * Throws AddressNotFoundException if no addresses are found, thus showing a 404.
     */
    public Mono<List<Address>> verifyAddress(String addressQuery) {
        // wrap the cached call in a Mono
        return Mono.fromFuture(
                cache.get(addressQuery, (key, executor) -> {
                    // must return CompletableFuture<List<Address>>

                    if(Objects.equals(key, "")){
                        //prevent further execution, and pass an error
                        throw new AddressNotFoundException("No addresses found for: " + key);
                    }

                    return client.queryByCql("full_address ILIKE '" + key + "%'")
                            .map(collection -> {
                                List<Address> addresses = collection.getFeatures().stream()
                                        .map(f -> {
                                            var props = f.getProperties();
                                            return new Address(
                                                    (String) props.get("full_address"),
                                                    (String) props.get("suburb_locality"),
                                                    (String) props.get("town_city")
                                            );
                                        })
                                        .collect(Collectors.toList());

                                if (addresses.isEmpty()) {
                                    // throw to skip caching empty results
                                    throw new AddressNotFoundException("No addresses found for: " + key);
                                }
                                return addresses;
                            })
                            .toFuture(); // ✅ returns CompletableFuture<List<Address>>
                })
        );
    }
}