package com.retail.inventory;

/**
 * Exception thrown when product stock is insufficient.
 */
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
