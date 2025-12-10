package com.diwakarallu.ecommerce.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
                @NotBlank(message = "Name is required") @Size(max = 100, message = "Name cannot exceed 100 characters") String name,

                @NotBlank(message = "Description is required") String description,

                @NotBlank(message = "SKU code is required") String skuCode,

                @NotNull(message = "Price is required") BigDecimal price) {
}

/*
 * 
 * // A record is a special kind of class introduced in Java 16+ for immutable
 * data carriers.
 *
 * Records automatically provide:
 * 
 * Private final fields
 * 
 * Constructor
 * 
 * Getters (called id(), name(), etc.)
 * 
 * equals(), hashCode(), toString()
 * 
 * Immutable → fields cannot be changed after creation
 */