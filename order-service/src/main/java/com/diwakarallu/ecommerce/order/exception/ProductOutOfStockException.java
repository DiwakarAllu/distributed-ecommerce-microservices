package com.diwakarallu.ecommerce.order.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String msg) {
        super(msg);
    }
}
