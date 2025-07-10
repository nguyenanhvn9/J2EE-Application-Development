package Tuan01;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double powerConsumption;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, double powerConsumption) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public void display() {
        System.out.printf("Electronic Product: ID=%s, Name=%s, Price=%.2f, Quantity=%d, Warranty=%d months, Power=%.2fW%n",
                id, name, price, quantityInStock, warrantyMonths, powerConsumption);
    }
}
