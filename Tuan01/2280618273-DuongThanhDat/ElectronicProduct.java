package Tuan01;

public class ElectronicProduct extends Product {
    private int warrantyPeriodMonths;
    private double powerConsumptionWatts;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyPeriodMonths, double powerConsumptionWatts) {
        super(id, name, price, quantityInStock);
        this.warrantyPeriodMonths = warrantyPeriodMonths;
        this.powerConsumptionWatts = powerConsumptionWatts;
    }

    // Getters
    public int getWarrantyPeriodMonths() {
        return warrantyPeriodMonths;
    }

    public double getPowerConsumptionWatts() {
        return powerConsumptionWatts;
    }

    // Setters
    public void setWarrantyPeriodMonths(int warrantyPeriodMonths) {
        this.warrantyPeriodMonths = warrantyPeriodMonths;
    }

    public void setPowerConsumptionWatts(double powerConsumptionWatts) {
        this.powerConsumptionWatts = powerConsumptionWatts;
    }

    @Override
    public void display() {
        System.out.println("--- Electronic Product ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " VND");
        System.out.println("Quantity in Stock: " + quantityInStock);
        System.out.println("Warranty Period: " + warrantyPeriodMonths + " months");
        System.out.println("Power Consumption: " + powerConsumptionWatts + " Watts");
        System.out.println("--------------------------");
    }
}
