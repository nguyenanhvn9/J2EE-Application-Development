package com.example;

public class ElectronicProduct extends Product {
    private int warrantyMonths;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public void display() {
        System.out.println("=== Electronic Product ===");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + quantityInStock);
        System.out.println("Warranty: " + warrantyMonths + " months");
    }
}

