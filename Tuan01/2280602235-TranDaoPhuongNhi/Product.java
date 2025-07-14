// Lớp trừu tượng cho các sản phẩm
public abstract class Product {
    // Mã sản phẩm
    protected String id;
    // Tên sản phẩm
    protected String name;
    // Giá sản phẩm
    protected double price;
    // Số lượng tồn kho
    protected int quantityInStock;

    public Product(String id, String name, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }

    // Phương thức trừu tượng hiển thị thông tin sản phẩm
    public abstract void display();
} 