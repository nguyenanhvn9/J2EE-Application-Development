import java.time.LocalDate;

public class FoodProduct extends Product {
    private LocalDate mfgDate;
    private LocalDate expDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate mfgDate, LocalDate expDate) {
        super(id, name, price, quantityInStock);
        this.mfgDate = mfgDate;
        this.expDate = expDate;
    }

    @Override
    public void display() {
        System.out.printf("Food - ID: %s, Name: %s, Price: %.2f, Stock: %d, MFG: %s, EXP: %s%n",
                id, name, price, quantityInStock, mfgDate, expDate);
    }
}