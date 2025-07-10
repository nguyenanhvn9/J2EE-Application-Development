package model;

public abstract class Product {
    protected String id;
    protected String name;
    protected double price;
    protected int quantityInStock;

    public Product(String id, String name, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public abstract void display();

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantityInStock() { return quantityInStock; }

    public void updateQuantity(int amount) {
        this.quantityInStock += amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
