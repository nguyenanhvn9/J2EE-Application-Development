package com.retail.inventory.model;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double powerConsumption;

    public ElectronicProduct(String id, String name, double price, int quantityInStock,
                             int warrantyMonths, double powerConsumption) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public void display() {
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("📱 ELECTRONIC PRODUCT\n");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("🆔 ID: %s\n", id);
        System.out.printf("📦 Name: %s\n", name);
        System.out.printf("💰 Price: $%.2f\n", price);
        System.out.printf("📊 Stock: %d units\n", quantityInStock);
        System.out.printf("🛡️ Warranty: %d months\n", warrantyMonths);
        System.out.printf("⚡ Power: %.2f W\n", powerConsumption);
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

    // Getters and Setters
    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }

    public double getPowerConsumption() { return powerConsumption; }
    public void setPowerConsumption(double powerConsumption) { this.powerConsumption = powerConsumption; }
}
