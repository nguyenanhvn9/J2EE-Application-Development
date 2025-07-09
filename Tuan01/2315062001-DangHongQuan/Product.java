abstract class Product {
    protected int id;
    protected String name;
    protected double price;
    protected int quantityInStock;

    public Product(int id, String name, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public abstract void display();
}
