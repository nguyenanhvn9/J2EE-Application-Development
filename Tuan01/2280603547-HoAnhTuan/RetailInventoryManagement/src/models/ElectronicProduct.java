package models;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int powerConsumption; // Đổi tên rõ ràng hơn

    public ElectronicProduct(String id, String name, double price, int quantity, int warrantyMonths, int powerConsumption) {
        super(id, name, price, quantity);
        this.warrantyMonths = warrantyMonths;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public void display() {
        System.out.printf("Electronic Product | ID: %s | Name: %s | Price: %.2f | Stock: %d | Warranty: %d months | Power: %d W%n",
                          id, name, price, quantityInStock, warrantyMonths, powerConsumption);
    }

    // Getters cho thuộc tính riêng
    public int getWarrantyMonths() { return warrantyMonths; }
    public int getPowerConsumption() { return powerConsumption; }
}