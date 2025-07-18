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
        System.out.println("[Thuc pham] " + name + " - " + price + " VND - So luong: " + quantityInStock +
                " | NSX: " + manufactureDate + ", HSD: " + expiryDate);
    }
}
