package Tuan01;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate manufactureDate, LocalDate expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.printf("Food Product: ID=%s, Name=%s, Price=%.2f, Quantity=%d, Manufacture Date=%s, Expiry Date=%s%n",
                id, name, price, quantityInStock, manufactureDate, expiryDate);
    }
}
