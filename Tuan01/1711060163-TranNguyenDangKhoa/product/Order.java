package product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private static int orderCount = 0;

    public Order() {
        orderCount++;
        this.orderId = "ORD" + orderCount;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho!");
        }

        items.add(new OrderItem(product, quantity));
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
        System.out.println("Đã thêm " + quantity + " " + product.getName() + " vào đơn hàng");
    }

    public void displayOrder() {
        System.out.println("\n=== Đơn hàng " + orderId + " ===");
        System.out.println("Ngày đặt: " + orderDate);

        if (items.isEmpty()) {
            System.out.println("Đơn hàng trống");
            return;
        }

        double total = 0;
        for (OrderItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            double subtotal = p.getPrice() * qty;
            total += subtotal;

            System.out.printf("%s - SL: %d - Đơn giá: %.2f - Thành tiền: %.2f\n",
                    p.getName(), qty, p.getPrice(), subtotal);
        }
        System.out.printf("Tổng tiền: %.2f\n", total);
    }
}