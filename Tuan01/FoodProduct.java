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
        System.out.printf("Thuc pham: %s | %s | Gia: %.2f | SL: %d | NSX: %s | HSD: %s\n",
                id, name, price, quantityInStock, manufactureDate, expiryDate);
    }
}