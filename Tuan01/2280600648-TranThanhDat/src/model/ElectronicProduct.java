/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DatG
 */
public class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public int getPower() {
        return power;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }
    @Override
    public void display() {
        System.out.printf("[Điện tử] %s | %s | %.2f | SL: %d | BH: %d tháng | Công suất: %dW\n",
                id, name, price, quantityInStock, warrantyMonths, power);
    }
}
