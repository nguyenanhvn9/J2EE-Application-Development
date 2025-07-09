import java.util.*;

// Lớp quản lý đơn hàng
public class Order {
    // Map lưu sản phẩm và số lượng mua
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    // Thêm sản phẩm vào đơn hàng
    public void addItem(Product product, int quantity) {
        items.put(product, quantity);
    }

    // Xử lý đơn hàng: kiểm tra tồn kho và cập nhật số lượng
    public void processOrder() throws OutOfStockException {
        // Kiểm tra tồn kho trước
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Sản phẩm '" + product.getName() + "' không đủ số lượng trong kho!");
            }
        }
        // Nếu đủ, trừ số lượng trong kho
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
        System.out.println("Đơn hàng đã được xử lý thành công!");
    }

    // Hiển thị thông tin đơn hàng
    public void displayOrder() {
        System.out.println("--- Thông tin đơn hàng ---");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.print("Sản phẩm: ");
            product.display();
            System.out.println("Số lượng mua: " + quantity);
        }
    }
} 