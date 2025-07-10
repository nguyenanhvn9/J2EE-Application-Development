package Tuan01;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate productionDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate productionDate, LocalDate expiryDate) {
        super(id, name, price, quantityInStock);
        this.productionDate = productionDate;
        this.expiryDate = expiryDate;
    }

    // Getters
    public LocalDate getProductionDate() {
        return productionDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    // Setters
    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("--- Food Product ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " VND");
        System.out.println("Quantity in Stock: " + quantityInStock);
        System.out.println("Production Date: " + productionDate);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("----------------------");
    }
}
