package model;

public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantity, String mfg, String exp) {
        super(id, name, price, quantity);
        this.manufactureDate = mfg;
        this.expiryDate = exp;
    }

    @Override
    public void display() {
        System.out.printf("[Food] ID: %s | Name: %s | Price: %.2f | Stock: %d | MFG: %s | EXP: %s%n",
                id, name, price, quantityInStock, manufactureDate, expiryDate);
    }
}
