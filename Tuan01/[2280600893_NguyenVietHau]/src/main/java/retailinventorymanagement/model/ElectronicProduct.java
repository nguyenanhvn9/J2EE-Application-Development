package retailinventorymanagement.model;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dtnhn
 */
public class ElectronicProduct extends Product implements Serializable{
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("Electronics - ID: " + id + ", Name: " + name + ", Price: " + formatPrice() + 
            ", Qty: " + quantityInStock + ", Warranty: " + warrantyMonths + " months, Power: " + power + "W");
    }
    
    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public int getPower() {
        return power;
    }
}

