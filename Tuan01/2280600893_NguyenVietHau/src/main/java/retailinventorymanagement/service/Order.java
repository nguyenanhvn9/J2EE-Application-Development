/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailinventorymanagement.service;

/**
 *
 * @author dtnhn
 */
import java.io.Serializable;
import java.util.*;
import retailinventorymanagement.exception.OutOfStockException;
import retailinventorymanagement.model.Product;

public class Order implements Serializable{
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Product " + product.getName() + " is out of stock.");
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println("Product: " + entry.getKey().getName() + ", Quantity: " + entry.getValue());
        }
    }
}

