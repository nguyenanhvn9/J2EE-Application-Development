package Tuan01;

import Tuan01.models.Product;
import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        products = new ArrayList<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Thêm sản phẩm mới
    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                System.out.println("Sản phẩm với ID này đã tồn tại!");
                return;
            }
        }
        products.add(product);
    }

    // Xóa sản phẩm theo ID
    public boolean removeProductById(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    // Cập nhật thông tin sản phẩm (giá, số lượng)
    public boolean updateProduct(String id, double newPrice, int newQuantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setPrice(newPrice);
                p.setQuantityInStock(newQuantity);
                return true;
            }
        }
        return false;
    }

    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                result.add(p);
            }
        }
        return result;
    }

    // Hiển thị tất cả sản phẩm trong kho
    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Kho hàng trống.");
            return;
        }
        for (Product p : products) {
            p.display();
        }
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Giảm số lượng sản phẩm khi đặt hàng
    public boolean reduceProductQuantity(String id, int quantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                if (p.getQuantityInStock() >= quantity) {
                    p.setQuantityInStock(p.getQuantityInStock() - quantity);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}