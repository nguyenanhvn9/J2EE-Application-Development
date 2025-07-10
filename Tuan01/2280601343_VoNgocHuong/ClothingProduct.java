package InventorySystem;

public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("[Clothing] ID: " + id + ", Name: " + name + ", Price: " + price + ", Stock: " + quantityInStock + ", Size: " + size + ", Material: " + material);
    }
}
