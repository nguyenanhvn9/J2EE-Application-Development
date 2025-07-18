package Tuan01.models;

public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, String manufactureDate, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void display() {
        System.out.println("[Thực phẩm] ID: " + id + ", Tên: " + name + ", Giá: " + price + ", SL: " + quantityInStock + ", NSX: " + manufactureDate + ", HSD: " + expiryDate);
    }
}
