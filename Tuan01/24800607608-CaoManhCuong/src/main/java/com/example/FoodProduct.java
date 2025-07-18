package com.example;

public class FoodProduct extends Product {
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("=== Food Product ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + quantityInStock);
        System.out.println("Expiry Date: " + expiryDate);
    }
}
