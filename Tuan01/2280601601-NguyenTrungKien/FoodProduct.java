import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate manufactureDate, LocalDate expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void display() {
        System.out.println("[Thực phẩm] ID: " + id + ", Tên: " + name + ", Giá: " + price + ", SL: " + quantityInStock + ", NSX: " + manufactureDate + ", HSD: " + expiryDate);
    }
} 