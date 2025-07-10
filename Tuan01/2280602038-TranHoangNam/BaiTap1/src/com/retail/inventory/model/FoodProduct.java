package com.retail.inventory.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FoodProduct extends Product {
    private LocalDate manufacturingDate;
    private LocalDate expirationDate;

    public FoodProduct(String id, String name, double price, int quantityInStock,
                       LocalDate manufacturingDate, LocalDate expirationDate) {
        super(id, name, price, quantityInStock);
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("🍎 FOOD PRODUCT\n");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("🆔 ID: %s\n", id);
        System.out.printf("📦 Name: %s\n", name);
        System.out.printf("💰 Price: $%.2f\n", price);
        System.out.printf("📊 Stock: %d units\n", quantityInStock);
        System.out.printf("📅 Manufacturing Date: %s\n", manufacturingDate.format(formatter));
        System.out.printf("⏰ Expiration Date: %s\n", expirationDate.format(formatter));
        if (expirationDate.isBefore(LocalDate.now())) {
            System.out.printf("⚠️  WARNING: Product is EXPIRED!\n");
        }
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

    // Getters and Setters
    public LocalDate getManufacturingDate() { return manufacturingDate; }
    public void setManufacturingDate(LocalDate manufacturingDate) { this.manufacturingDate = manufacturingDate; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
}

