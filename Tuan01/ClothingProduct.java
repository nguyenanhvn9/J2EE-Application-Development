package Tuan01;
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
        System.out.printf("Clothing Product: ID=%s, Name=%s, Price=%.2f, Quantity=%d, Size=%s, Material=%s%n",
                id, name, price, quantityInStock, size, material);
    }
}