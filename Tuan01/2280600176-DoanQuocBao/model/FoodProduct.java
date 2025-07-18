package model;

import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantity, LocalDate mfgDate, LocalDate expDate) {
        super(id, name, price, quantity);
        this.manufacturingDate = mfgDate;
        this.expiryDate = expDate;
    }

    @Override
    public void display() {
        System.out.println("Thực phẩm - ID: " + id + ", Tên: " + name + ", Giá: " + price + ", SL: " + quantityInStock +
                ", NSX: " + manufacturingDate + ", HSD: " + expiryDate);
    }
}
