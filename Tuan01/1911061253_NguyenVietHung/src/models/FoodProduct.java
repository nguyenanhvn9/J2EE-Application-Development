package models;

public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantity, String manufactureDate, String expiryDate) {
        super(id, name, price, quantity);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("[Thực phẩm] " + name + " | Giá: " + price + " | Tồn kho: " + quantityInStock +
                   " | NSX: " + manufactureDate + " | HSD: " + expiryDate);
    }
}