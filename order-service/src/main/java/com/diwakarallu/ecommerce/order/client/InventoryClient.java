package com.diwakarallu.ecommerce.order.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.diwakarallu.ecommerce.order.exception.InventoryUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

        private final WebClient webClient;

        @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFallback")
        @Retry(name = "inventory")
        public boolean isInStock(String skuCode, int quantity) {
                log.info("Checking inventory for skuCode: {} and quantity: {}", skuCode, quantity);

                try {
                        Boolean result = webClient.get()
                                        .uri(uriBuilder -> uriBuilder
                                                        .path("/api/inventory")
                                                        .queryParam("skuCode", skuCode)
                                                        .queryParam("quantity", quantity)
                                                        .build())
                                        .retrieve()
                                        .bodyToMono(Boolean.class)
                                        .block(); // makes it synchronous

                        return Boolean.TRUE.equals(result);

                } catch (RuntimeException ex) {
                        log.error("Network or runtime error during inventory check: {}", ex.getMessage());
                        throw ex; // Rethrow so Resilience4j annotations catch it
                }
        }

        public boolean inventoryFallback(String skuCode, int quantity, Throwable ex) {
                log.error("Inventory service fallback executed. Root cause: {}",
                                ex != null ? ex.getMessage() : "Unknown");
                throw new InventoryUnavailableException("Inventory service unavailable. Please try later.");
        }
}
