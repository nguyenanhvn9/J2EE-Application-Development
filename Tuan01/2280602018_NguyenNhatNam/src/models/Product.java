package models;

public abstract class Product {
    public String id;
    public String name;
    public double price;
    public int quantityInStock;

    public Product(String id, String name, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public abstract void display();
}