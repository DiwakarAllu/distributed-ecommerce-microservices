package com.diwakarallu.ecommerce.product.controller;

import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.diwakarallu.ecommerce.product.dto.ProductRequest;
import com.diwakarallu.ecommerce.product.dto.ProductResponse;
import com.diwakarallu.ecommerce.product.mapper.ProductMapper;
import com.diwakarallu.ecommerce.product.model.Product;
import com.diwakarallu.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {

        ProductRequest request = new ProductRequest(
                "Phone",
                "Smartphone",
                "PH123",
                BigDecimal.TEN);

        Product savedProduct = Product.builder()
                .id("1")
                .name("Phone")
                .description("Smartphone")
                .skuCode("PH123")
                .price(BigDecimal.TEN)
                .build();

        Mockito.when(productService.createProduct(any(Product.class)))
                .thenReturn(savedProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.skuCode").value("PH123"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = Product.builder().id("1").name("A").build();
        Product p2 = Product.builder().id("2").name("B").build();

        Mockito.when(productService.getAllProducts())
                .thenReturn(List.of(p1, p2));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("A"));

    }

    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(productService.getProductById("999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/products/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
