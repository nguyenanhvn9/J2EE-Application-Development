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
        System.out.println("[Quan ao] " + name + " - " + price + " VND - So luong: " + quantityInStock +
                " | Size: " + size + ", Chat lieu: " + material);
    }
}