package Tuan01;

public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private String power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, String power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("[Dien tu] ID: " + id + ", Ten: " + name + ", Gia: " + price + ", SL: " + quantityInStock + ", Bao hanh: " + warrantyMonths + " thang, Cong suat: " + power);
    }
} 