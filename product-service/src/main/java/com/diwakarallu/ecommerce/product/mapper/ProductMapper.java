package com.diwakarallu.ecommerce.product.mapper;

import com.diwakarallu.ecommerce.product.dto.ProductRequest;
import com.diwakarallu.ecommerce.product.dto.ProductResponse;
import com.diwakarallu.ecommerce.product.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .skuCode(request.skuCode())
                .price(request.price())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSkuCode(),
                product.getPrice()
        );
    }
}
