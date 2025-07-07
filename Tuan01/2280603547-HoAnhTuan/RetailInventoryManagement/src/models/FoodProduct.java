package models; // Khai báo package models

import java.time.LocalDate; // Import lớp LocalDate để làm việc với ngày tháng

public class FoodProduct extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    // Constructor của FoodProduct
    public FoodProduct(String id, String name, double price, int quantity, LocalDate manufactureDate, LocalDate expiryDate) {
        super(id, name, price, quantity); // Gọi constructor của lớp cha (Product)
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    // Ghi đè phương thức display() để hiển thị thông tin chi tiết của FoodProduct
    @Override
    public void display() {
        System.out.printf("Food Product       | ID: %s | Name: %s | Price: %.2f | Stock: %d | Mfg Date: %s | Exp Date: %s%n",
                          id, name, price, quantityInStock, manufactureDate, expiryDate);
    }

    // Getters cho các thuộc tính riêng của FoodProduct
    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}