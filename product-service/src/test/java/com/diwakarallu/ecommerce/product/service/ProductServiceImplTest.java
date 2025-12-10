package com.diwakarallu.ecommerce.product.service;

import com.diwakarallu.ecommerce.product.exception.ResourceNotFoundException;
import com.diwakarallu.ecommerce.product.model.Product;
import com.diwakarallu.ecommerce.product.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void init() {
        sampleProduct = Product.builder()
                .id("123")
                .name("Phone")
                .description("Smartphone")
                .skuCode("PH123")
                .price(BigDecimal.TEN)
                .build();
    }

    // ---------------------------------------------------------
    // CREATE PRODUCT
    // ---------------------------------------------------------
    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product created = productService.createProduct(sampleProduct);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Phone");

        verify(productRepository, times(1)).save(sampleProduct);
    }

    // ---------------------------------------------------------
    // GET ALL PRODUCTS
    // ---------------------------------------------------------
    @Test
    void testGetAllProducts() {
        List<Product> list = List.of(sampleProduct);

        when(productRepository.findAll()).thenReturn(list);

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(1);
        assertThat(products.get(0).getSkuCode()).isEqualTo("PH123");

        verify(productRepository).findAll();
    }

    // ---------------------------------------------------------
    // GET PRODUCT BY ID
    // ---------------------------------------------------------
    @Test
    void testGetProductById_Found() {
        when(productRepository.findById("123")).thenReturn(Optional.of(sampleProduct));

        Optional<Product> result = productService.getProductById("123");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Phone");

        verify(productRepository).findById("123");
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById("999");

        assertThat(result).isEmpty();

        verify(productRepository).findById("999");
    }

    // ---------------------------------------------------------
    // UPDATE PRODUCT
    // ---------------------------------------------------------
    @Test
    void testUpdateProduct_Success() {
        Product updated = Product.builder()
                .name("Phone Updated")
                .description("New Desc")
                .skuCode("PH123")
                .price(BigDecimal.valueOf(20))
                .build();

        when(productRepository.findById("123")).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updated);

        Product result = productService.updateProduct("123", updated);

        assertThat(result.getName()).isEqualTo("Phone Updated");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(20));

        verify(productRepository).findById("123");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        Product updated = Product.builder().name("X").build();

        assertThatThrownBy(() -> productService.updateProduct("999", updated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with id 999");

        verify(productRepository).findById("999");
        verify(productRepository, never()).save(any(Product.class));
    }

    // ---------------------------------------------------------
    // DELETE PRODUCT
    // ---------------------------------------------------------
    @Test
    void testDeleteProduct_Success() {
        when(productRepository.existsById("123")).thenReturn(true);

        doNothing().when(productRepository).deleteById("123");

        productService.deleteProduct("123");

        verify(productRepository).deleteById("123");
    }

}
