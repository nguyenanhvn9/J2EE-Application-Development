public class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, String manufactureDate, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("[Thuc pham] ID: " + id + ", Ten: " + name + ", Gia: " + price + ", Ton kho: " + quantityInStock + ", NSX: " + manufactureDate + ", HSD: " + expiryDate);
    }
}
