import java.util.*;

public class Order {
    private Map<Product, Integer> items;
    private double total;

    public Order() {
        items = new LinkedHashMap<>();
        total = 0;
    }

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho.");
        }
        items.put(product, quantity);
        total += product.getPrice() * quantity;
    }

    public void processOrder() throws OutOfStockException {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho.");
            }
        }
        // Nếu đủ hàng, trừ số lượng
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
    }

    public void displayOrder() {
        System.out.println("--- Đơn hàng ---");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("Số lượng mua: " + entry.getValue());
        }
        System.out.println("Tổng tiền: " + total);
    }

    public double getTotal() {
        return total;
    }
} 