package Tuan01.models;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private double power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public double getPower() {
        return power;
    }

    @Override
    public void display() {
        System.out.println("[Điện tử] ID: " + id + ", Tên: " + name + ", Giá: " + price + ", SL: " + quantityInStock + ", Bảo hành: " + warrantyMonths + " tháng, Công suất: " + power + "W");
    }
}
