package com.diwakarallu.ecommerce.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/products")
    public Mono<String> productFallback() {
        return Mono.just("Product Service is unavailable");
    }

    @GetMapping("/orders")
    public Mono<String> orderFallback() {
        return Mono.just("Order Service is unavailable");
    }

    @GetMapping("/inventory")
    public Mono<String> inventoryFallback() {
        return Mono.just("Inventory Service is unavailable");
    }
}
