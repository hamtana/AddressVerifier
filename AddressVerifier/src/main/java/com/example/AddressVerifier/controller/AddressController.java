package com.example.AddressVerifier.controller;

import com.example.AddressVerifier.service.LinzAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final LinzAddressService service;

    public AddressController(LinzAddressService service){
        this.service = service;
    }

    @GetMapping("/verify")
    public ResponseEntity<Mono<String>> verify(
            @RequestParam String address){

        return ResponseEntity.ok(
        service.verifyAddress(address));
    }

}
