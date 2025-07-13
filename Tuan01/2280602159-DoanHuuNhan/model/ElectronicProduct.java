package model;

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
        System.out.println("[Electronic] ID: " + id + ", Name: " + name + ", Price: " + price + ", Stock: " + quantityInStock + ", Warranty: " + warrantyMonths + " months, Power: " + powerConsumption + "W");
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public double getPowerConsumption() { return powerConsumption; }
}
