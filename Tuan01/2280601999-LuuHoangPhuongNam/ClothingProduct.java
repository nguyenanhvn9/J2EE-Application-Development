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
        System.out.printf("Quan ao: %s | %s | Gia: %.2f | SL: %d | Size: %s | Chat lieu: %s\n",
                id, name, price, quantityInStock, size, material);
    }
}