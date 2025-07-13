package _2280601612_DuongTuanKiet;
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
        System.out.println("[Food] " + name + " - Price: " + price + ", Stock: " + quantityInStock + ", MFG: " + manufactureDate + ", EXP: " + expiryDate);
    }
}