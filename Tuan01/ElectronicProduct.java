package Tuan01;

public class ElectronicProduct extends Product {
    private float power;
    private int warrantyPeriod;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, float power, int warrantyPeriod) {
        super(id, name, price, quantityInStock);
        this.power = power;
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Price: " + getPrice());
        System.out.println("Quantity in Stock: " + getQuantityInStock());
        System.out.println("Power: " + power + "W");
        System.out.println("Warranty Period: " + warrantyPeriod);
    }

    public Float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    
}
