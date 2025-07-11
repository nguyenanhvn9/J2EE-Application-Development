package model;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public int getPower() { return power; }

    @Override
    public void display() {
        System.out.println("[Điện tử] ID: " + id + ", Tên: " + name + ", Giá: " + price + ", SL: " + quantityInStock + ", Bảo hành: " + warrantyMonths + " tháng, Công suất: " + power + "W");
    }
} 