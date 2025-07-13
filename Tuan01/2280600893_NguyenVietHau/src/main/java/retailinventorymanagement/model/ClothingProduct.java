/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailinventorymanagement.model;

import java.io.Serializable;

/**
 *
 * @author dtnhn
 */
public class ClothingProduct extends Product implements Serializable{
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("Clothing - ID: " + id + ", Name: " + name + ", Price: " + formatPrice() + 
            ", Qty: " + quantityInStock + ", Size: " + size + ", Material: " + material);
    }
    
    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }
}

