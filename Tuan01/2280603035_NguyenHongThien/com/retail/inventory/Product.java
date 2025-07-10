package com.retail.inventory;

import java.io.Serializable;

/**
 * Abstract base class for all products.
 */
public abstract class Product implements Serializable {
    private String id;
    private String name;
    private double price;
    private int quantityInStock;

    public Product(String id, String name, double price, int quantityInStock) {
        setId(id);
        setName(name);
        setPrice(price);
        setQuantityInStock(quantityInStock);
    }

    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.trim().isEmpty())
            throw new IllegalArgumentException("Product ID cannot be empty.");
        this.id = id.trim();
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty.");
        this.name = name.trim();
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) {
        if (quantityInStock < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantityInStock = quantityInStock;
    }

    /**
     * Display product details.
     */
    public abstract void display();
}
