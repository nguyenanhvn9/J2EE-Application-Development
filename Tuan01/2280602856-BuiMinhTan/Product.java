public class Product {
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

    public void display() {
        System.out.printf("Product: ID=%s, Name=%s, Price=%.2f, Qty=%d\n",
                          this.id, this.name, this.price, this.quantityInStock);
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public double getPrice() { return this.price; }
    public int getQuantityInStock() { return this.quantityInStock; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }
}
