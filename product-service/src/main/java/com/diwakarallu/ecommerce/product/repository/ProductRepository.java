package com.diwakarallu.ecommerce.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diwakarallu.ecommerce.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    /**
     * MongoRepository provides all the basic operations:
     * 
     * save(Product product) → insert or update returns the saved product 
     * 
     * findAll() → get List of all products
     * 
     * findById(String id) → get a Optional product by ID
     * 
     * delete(Product product) → delete a product
     * 
     * deleteById(String id) → delete by ID
     */
}
