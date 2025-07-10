package Tuan01;

public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    // Getters
    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    // Setters
    public void setSize(String size) {
        this.size = size;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("--- Clothing Product ---");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " VND");
        System.out.println("Quantity in Stock: " + quantityInStock);
        System.out.println("Size: " + size);
        System.out.println("Material: " + material);
        System.out.println("------------------------");
    }
}
