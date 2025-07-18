package com.retail.inventory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a food product.
 */
public class FoodProduct extends Product {
    private LocalDate productionDate;
    private LocalDate expirationDate;

    public FoodProduct(String id, String name, double price, int quantityInStock,
                       LocalDate productionDate, LocalDate expirationDate) {
        super(id, name, price, quantityInStock);
        setProductionDate(productionDate);
        setExpirationDate(expirationDate);
    }

    public LocalDate getProductionDate() { return productionDate; }
    public void setProductionDate(LocalDate productionDate) {
        if (productionDate == null)
            throw new IllegalArgumentException("Production date cannot be null.");
        this.productionDate = productionDate;
    }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null)
            throw new IllegalArgumentException("Expiration date cannot be null.");
        if (expirationDate.isBefore(productionDate))
            throw new IllegalArgumentException("Expiration date must be after production date.");
        this.expirationDate = expirationDate;
    }

    @Override
    public void display() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.printf("FoodProduct [ID=%s, Name=%s, Price=%.2f, Stock=%d, Production=%s, Expiration=%s]%n",
                getId(), getName(), getPrice(), getQuantityInStock(),
                productionDate.format(fmt), expirationDate.format(fmt));
    }
}
