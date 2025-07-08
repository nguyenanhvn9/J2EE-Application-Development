/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DatG
 */
public class FoodProduct  extends Product {
    private String mfgDate;
    private String expDate;

    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
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

    public String getMfgDate() {
        return mfgDate;
    }

    public String getExpDate() {
        return expDate;
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

    public FoodProduct(String id, String name, double price, int quantityInStock, String mfgDate, String expDate ) {
        super(id, name, price, quantityInStock);
        this.mfgDate = mfgDate;
        this.expDate = expDate;
    }
    @Override
    public void display() {
        System.out.printf("[Thực phẩm] ID: %s | Tên: %s | Giá: %.2f | SL: %d | NSX: %s | HSD: %s\n",
                id, name, price, quantityInStock, mfgDate, expDate);
    }
}
