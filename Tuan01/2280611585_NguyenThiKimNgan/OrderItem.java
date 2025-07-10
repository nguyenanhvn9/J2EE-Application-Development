package Tuan01;

public class OrderItem {

    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice(){
        return product.getPrice() * quantity;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void displayInfo() {
        System.out.println("San pham: " + product.getName() +
                           ", So luong: " + quantity +
                           ", Don gia: " + product.getPrice() +
                           ", Thanh tien: " + getTotalPrice());
    }
}