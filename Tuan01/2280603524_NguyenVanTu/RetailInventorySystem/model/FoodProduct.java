package Tuan01.RetailInventorySystem.model;

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
        System.out.printf("[Food] ID: %s | Name: %s | Price: %.2f | Stock: %d | MFG: %s | EXP: %s\n",
                id, name, price, quantityInStock, manufactureDate, expiryDate);
    }
}
