package model;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate productionDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate productionDate, LocalDate expiryDate) {
        super(id, name, price, quantityInStock);
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("[Food] ID: " + id + ", Name: " + name + ", Price: " + price + ", Stock: " + quantityInStock + ", Production: " + productionDate + ", Expiry: " + expiryDate);
    }

    public LocalDate getProductionDate() { return productionDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
}
