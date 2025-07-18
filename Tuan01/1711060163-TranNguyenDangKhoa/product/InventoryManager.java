package product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        this.products = new ArrayList<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public Product findProductById(String id) {
        if (id != null && !id.trim().isEmpty()) {
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
            System.out.println("Đã thêm sản phẩm: " + product.getName());
        }
    }

    public boolean removeProduct(String id) {
        if (id != null && !id.trim().isEmpty()) {
            boolean removed = products.removeIf(p -> p.getId().equals(id));
            if (removed) {
                System.out.println("Đã xóa sản phẩm có ID: " + id);
            }
            return removed;
        }
        return false;
    }

    public boolean updatePrice(String id, float newPrice) {
        if (newPrice >= 0) {
            for (Product p : products) {
                if (p.getId().equals(id)) {
                    p.setPrice(newPrice);
                    System.out.println("Đã cập nhật giá sản phẩm " + p.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateQuantity(String id, int newQuantity) {
        if (newQuantity >= 0) {
            for (Product p : products) {
                if (p.getId().equals(id)) {
                    p.setQuantityInStock(newQuantity);
                    System.out.println("Đã cập nhật số lượng sản phẩm " + p.getName());
                    return true;
                }
            }
        }
        return false;
    }

    public List<Product> searchByName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<Product> searchByPriceRange(float minPrice, float maxPrice) {
        if (minPrice <= maxPrice) {
            return products.stream()
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void processOrder(String productId, int quantity, Order order) throws OutOfStockException {
        Product product = findProductById(productId);
        if (product == null) {
            throw new OutOfStockException("Không tìm thấy sản phẩm có ID: " + productId);
        }

        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Số lượng trong kho không đủ");
        }

        order.addItem(product, quantity);
    }


    public void displayAllProducts() {
        System.out.println("\n=== Danh sách sản phẩm trong kho ===");
        if (products.isEmpty()) {
            System.out.println("Kho không có sản phẩm nào.");
            return;
        }
        for (Product p : products) {
            p.display();
            System.out.println("-------------------");
        }
    }
}