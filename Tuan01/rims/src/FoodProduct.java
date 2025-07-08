public class FoodProduct extends Product {
    private String expirationDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, String expirationDate) {
        super(id, name, price, quantityInStock);
        this.expirationDate = expirationDate;
    }

    @Override
    public void display() {
        System.out.println("Food: " + name + ", ID: " + id + ", Price: " + price + ", Qty: " + quantityInStock + ", Exp: " + expirationDate);
    }
}
