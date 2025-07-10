package com.retail.inventory.model;
import java.io.Serializable;
import java.util.Objects;
public abstract class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected double price;
    protected int quantityInStock;





    public Product(String id, String name, double price, int quantityInStock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public abstract void display();
    public String getId(){return id;}
    public void setId(String id){this.id = id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(id,product.id);
    }
    @Override
    public int hashCode(){
        return  Objects.hash(id);
    }
}
