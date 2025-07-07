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

  public String getId() {
    return id;
  }

  public String getName() { // Thêm getter cho name
    return name;
  }

  public double getPrice() { // Thêm getter cho price
    return price;
  }

  public int getQuantityInStock() {
    return quantityInStock;
  }

  public void setQuantityInStock(int quantity) {
    this.quantityInStock = quantity;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public abstract void display();
}
