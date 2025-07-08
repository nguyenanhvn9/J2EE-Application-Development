package Tuan01.model;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantity, LocalDate mfgDate, LocalDate expDate) {
        super(id, name, price, quantity);
        this.manufactureDate = mfgDate;
        this.expiryDate = expDate;
    }

    @Override
    public void display() {
        System.out.println("🍎 [Food] ID: " + id + ", Name: " + name + ", Price: " + price +
                ", Quantity: " + quantityInStock + ", MFG: " + manufactureDate + ", EXP: " + expiryDate);
    }
}
