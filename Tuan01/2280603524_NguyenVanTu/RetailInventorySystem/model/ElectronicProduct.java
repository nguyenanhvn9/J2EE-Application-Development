package Tuan01.RetailInventorySystem.model;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantity, int warrantyMonths, int power) {
        super(id, name, price, quantity);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.printf("[Electronic] ID: %s | Name: %s | Price: %.2f | Stock: %d | Warranty: %d months | Power: %dW\n",
                id, name, price, quantityInStock, warrantyMonths, power);
    }
}
