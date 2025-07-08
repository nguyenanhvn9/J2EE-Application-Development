public class ClothingProduct extends Product {
    private String size;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size) {
        super(id, name, price, quantityInStock);
        this.size = size;
    }

    @Override
    public void display() {
        System.out.println("Clothing: " + name + ", ID: " + id + ", Price: " + price + ", Qty: " + quantityInStock + ", Size: " + size);
    }
}
