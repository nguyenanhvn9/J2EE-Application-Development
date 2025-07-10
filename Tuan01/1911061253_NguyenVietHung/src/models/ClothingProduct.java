package models;

public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantity, String size, String material) {
        super(id, name, price, quantity);
        this.size = size;
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("[Quần áo] " + name + " | Giá: " + price + " | Tồn kho: " + quantityInStock +
                   " | Size: " + size + " | Chất liệu: " + material);
    }
}