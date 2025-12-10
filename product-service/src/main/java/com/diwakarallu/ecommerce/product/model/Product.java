package com.diwakarallu.ecommerce.product.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Document(value = "product") - Alias for collection, older usage
@Document(collection = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String id; // maps to MongoDB _id
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price; // Always use BigDecimal in Java for money, price, tax, discounts, etc. ? No rounding errors & Exact results
}