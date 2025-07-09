package entity;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantity, LocalDate manufactureDate, LocalDate expiryDate) {
        super(id, name, price, quantity);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("Food - ID: " + id + ", Name: " + name + ", Price: " + price +
                ", Stock: " + quantityInStock + ", MFG: " + manufactureDate + ", EXP: " + expiryDate);
    }
}
