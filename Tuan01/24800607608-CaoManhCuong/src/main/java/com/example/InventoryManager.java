package com.example;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        products = new ArrayList<>();
    }

    // Singleton: chỉ có 1 instance duy nhất
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Thêm sản phẩm
    public void addProduct(Product product) {
        products.add(product);
        System.out.println("✔ Đã thêm sản phẩm: " + product.name);
    }

    // Xóa sản phẩm theo ID
    public void removeProductById(String id) {
        Product toRemove = null;
        for (Product p : products) {
            if (p.id.equals(id)) {
                toRemove = p;
                break;
            }
        }

        if (toRemove != null) {
            products.remove(toRemove);
            System.out.println("✔ Đã xóa sản phẩm có ID: " + id);
        } else {
            System.out.println("❌ Không tìm thấy sản phẩm có ID: " + id);
        }
    }

    // Cập nhật giá và số lượng
    public void updateProduct(String id, double newPrice, int newQuantity) {
        for (Product p : products) {
            if (p.id.equals(id)) {
                p.price = newPrice;
                p.quantityInStock = newQuantity;
                System.out.println("✔ Đã cập nhật sản phẩm có ID: " + id);
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sản phẩm có ID: " + id);
    }

    // Tìm theo tên (không phân biệt hoa thường)
    public void searchByName(String keyword) {
        System.out.println("🔍 Kết quả tìm kiếm theo tên:");
        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                p.display();
                System.out.println();
            }
        }
    }

    // Tìm theo khoảng giá
    public void searchByPriceRange(double min, double max) {
        System.out.println("🔍 Kết quả tìm theo khoảng giá: $" + min + " - $" + max);
        for (Product p : products) {
            if (p.price >= min && p.price <= max) {
                p.display();
                System.out.println();
            }
        }
    }

    // Hiển thị toàn bộ sản phẩm
    public void displayAllProducts() {
        System.out.println("📦 Danh sách tất cả sản phẩm trong kho:");
        for (Product p : products) {
            p.display();
            System.out.println();
        }
    }

    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.id.equalsIgnoreCase(id)) return p;
        }
        return null;
    }

}

