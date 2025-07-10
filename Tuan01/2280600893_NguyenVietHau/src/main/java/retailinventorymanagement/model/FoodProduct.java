/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailinventorymanagement.model;

/**
 *
 * @author dtnhn
 */
import java.io.Serializable;
import java.time.LocalDate;

public class FoodProduct extends Product implements Serializable{
    private LocalDate mfgDate;
    private LocalDate expDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate mfgDate, LocalDate expDate) {
        super(id, name, price, quantityInStock);
        this.mfgDate = mfgDate;
        this.expDate = expDate;
    }

    @Override
    public void display() {
        System.out.println("Food - ID: " + id + ", Name: " + name + ", Price: " + formatPrice() + 
            ", Qty: " + quantityInStock + ", MFG: " + mfgDate + ", EXP: " + expDate);
    }
    
    public LocalDate getManufactureDate() {
        return mfgDate;
    }

    public LocalDate getExpiryDate() {
        return expDate;
    }
}

