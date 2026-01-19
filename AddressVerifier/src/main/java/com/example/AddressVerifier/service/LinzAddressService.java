package com.example.AddressVerifier.service;

import com.example.AddressVerifier.client.LinzWfsClient;
import org.springframework.stereotype.Service;

@Service
public class LinzAddressService {

    private final LinzWfsClient client;

    public LinzAddressService(LinzWfsClient client){
        this.client = client;
    }

    public String verifyAddress(String address) {

        String safeAddress = address.replace("'", "''");

        String cql = String.format("full_address ILIKE '%s%%'", safeAddress);

        return client.queryByCql(cql);
    }


}
