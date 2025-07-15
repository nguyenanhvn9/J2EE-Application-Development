import java.util.*;

public class Order {
    private Map<Product, Integer> orderItems;
    private double totalAmount;

    public Order() {
        orderItems = new LinkedHashMap<>();
        totalAmount = 0;
    }

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Không đủ hàng trong kho cho sản phẩm: " + product.getName());
        }
        orderItems.put(product, quantity);
        totalAmount += product.getPrice() * quantity;
    }

    public void processOrder() throws OutOfStockException {
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Không đủ hàng trong kho cho sản phẩm: " + product.getName());
            }
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void displayOrder() {
        System.out.println("--- Đơn hàng ---");
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.print("Sản phẩm: ");
            product.display();
            System.out.println("Số lượng mua: " + quantity);
        }
        System.out.println("Tổng tiền: " + totalAmount);
    }
} 