public class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock,
                           String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    public void display() {
        System.out.printf("Clothing: ID=%s, Name=%s, Price=%.2f, Qty=%d, Size=%s, Material=%s\n",
                          this.id, this.name, this.price, this.quantityInStock, this.size, this.material);
    }
}
