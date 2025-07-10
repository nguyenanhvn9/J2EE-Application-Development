package com.retail.inventory;

/**
 * Represents an electronic product.
 */
public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double powerConsumption;

    public ElectronicProduct(String id, String name, double price, int quantityInStock,
                             int warrantyMonths, double powerConsumption) {
        super(id, name, price, quantityInStock);
        setWarrantyMonths(warrantyMonths);
        setPowerConsumption(powerConsumption);
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0)
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        this.warrantyMonths = warrantyMonths;
    }

    public double getPowerConsumption() { return powerConsumption; }
    public void setPowerConsumption(double powerConsumption) {
        if (powerConsumption < 0)
            throw new IllegalArgumentException("Power consumption cannot be negative.");
        this.powerConsumption = powerConsumption;
    }

    @Override
    public void display() {
        System.out.printf("ElectronicProduct [ID=%s, Name=%s, Price=%.2f, Stock=%d, Warranty=%d months, Power=%.2fW]%n",
                getId(), getName(), getPrice(), getQuantityInStock(), warrantyMonths, powerConsumption);
    }
}
