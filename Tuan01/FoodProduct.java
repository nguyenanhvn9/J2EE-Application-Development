package Tuan01;

public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock,
                       String manufactureDate, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.printf("Food: ID=%s, Name=%s, Price=%.2f, Qty=%d, MFG=%s, EXP=%s\n",
                          id, name, price, quantityInStock, manufactureDate, expiryDate);
    }
}

