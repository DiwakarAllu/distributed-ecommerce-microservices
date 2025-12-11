package com.diwakarallu.ecommerce.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diwakarallu.ecommerce.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);
}
