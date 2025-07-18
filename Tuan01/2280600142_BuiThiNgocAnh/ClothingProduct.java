// Lớp sản phẩm quần áo kế thừa Product
public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    // Getter và Setter
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    // Hiển thị thông tin sản phẩm quần áo
    @Override
    public void display() {
        System.out.println("[Quần áo] Mã: " + id + ", Tên: " + name + ", Giá: " + price + ", Tồn kho: " + quantityInStock + ", Size: " + size + ", Chất liệu: " + material);
    }
} 