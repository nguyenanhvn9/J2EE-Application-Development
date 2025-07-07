package manager;

import models.*; // Import tất cả các lớp Product từ package models
import java.util.*; // Import List, Map, HashMap

public class InventoryManager {
    // 1. Instance tĩnh và private của lớp (singleton)
    private static InventoryManager instance;
    // 2. Map để lưu trữ sản phẩm (ID là khóa, Product là giá trị)
    private Map<String, Product> products;

    // 3. Constructor private để ngăn việc tạo đối tượng từ bên ngoài
    private InventoryManager() {
        products = new HashMap<>();
    }

    // 4. Phương thức static công khai để lấy instance duy nhất
    public static InventoryManager getInstance() {
        if (instance == null) {
            // Đảm bảo an toàn luồng nếu có nhiều luồng gọi đồng thời (Double-checked locking)
            synchronized (InventoryManager.class) {
                if (instance == null) {
                    instance = new InventoryManager();
                }
            }
        }
        return instance;
    }

    // Thêm sản phẩm mới
    public void addProduct(Product p) {
        if (products.containsKey(p.getId())) {
            System.out.println("Warning: Product with ID " + p.getId() + " already exists. Overwriting.");
        }
        products.put(p.getId(), p);
    }

    // Xóa sản phẩm theo ID
    public boolean removeProduct(String id) {
        return products.remove(id) != null; // Trả về true nếu xóa thành công, false nếu không tìm thấy
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(String id) {
        return products.get(id);
    }

    // Cập nhật thông tin sản phẩm (giá và số lượng)
    public boolean updateProduct(String id, double newPrice, int newQuantity) {
        Product p = products.get(id);
        if (p == null) {
            return false; // Sản phẩm không tồn tại
        }
        p.setPrice(newPrice);
        p.setQuantityInStock(newQuantity);
        return true; // Cập nhật thành công
    }

    // Tìm kiếm sản phẩm theo tên (tìm kiếm một phần, không phân biệt chữ hoa/thường)
    public List<Product> searchByName(String name) {
        List<Product> results = new ArrayList<>();
        String lowerCaseName = name.toLowerCase();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(lowerCaseName)) {
                results.add(p);
            }
        }
        return results;
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        List<Product> results = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                results.add(p);
            }
        }
        return results;
    }

    // Hiển thị tất cả sản phẩm trong kho (sử dụng đa hình)
    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Current Inventory ---");
        for (Product p : products.values()) {
            p.display(); // Gọi phương thức display() của từng loại sản phẩm cụ thể
        }
        System.out.println("-------------------------");
    }
}