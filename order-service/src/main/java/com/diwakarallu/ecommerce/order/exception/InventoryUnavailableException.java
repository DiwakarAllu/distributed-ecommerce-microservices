package com.diwakarallu.ecommerce.order.exception;

public class InventoryUnavailableException extends RuntimeException{
		public InventoryUnavailableException(String msg) {
			super(msg);
		}
}
