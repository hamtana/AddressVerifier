package com.example.AddressVerifier.controller;

import com.example.AddressVerifier.exception.AddressNotFoundException;
import com.example.AddressVerifier.model.Address;
import com.example.AddressVerifier.service.LinzAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final LinzAddressService service;

    public AddressController(LinzAddressService service){
        this.service = service;
    }

    @GetMapping("/verify")
    public Mono<ResponseEntity<List<Address>>> verify(@RequestParam String address) {
        return service.verifyAddress(address)
                .map(ResponseEntity::ok) // wraps the Address in 200 OK
                .onErrorResume(AddressNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }

}
