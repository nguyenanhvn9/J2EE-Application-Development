import java.util.*;

// Lớp quản lý kho hàng (Singleton)
public class InventoryManager {
    // Thể hiện duy nhất của InventoryManager
    private static InventoryManager instance;
    // Map lưu trữ sản phẩm theo id
    private Map<String, Product> products;

    // Hàm khởi tạo private để đảm bảo Singleton
    private InventoryManager() {
        products = new HashMap<>();
    }

    // Lấy thể hiện duy nhất
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Thêm sản phẩm mới
    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    // Xóa sản phẩm theo id
    public boolean removeProduct(String id) {
        return products.remove(id) != null;
    }

    // Cập nhật giá và số lượng sản phẩm
    public boolean updateProduct(String id, double newPrice, int newQuantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(newPrice);
            p.setQuantityInStock(newQuantity);
            return true;
        }
        return false;
    }

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

    // Hiển thị tất cả sản phẩm trong kho
    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Kho hàng trống!");
        } else {
            for (Product p : products.values()) {
                p.display();
            }
        }
    }

    // Lấy sản phẩm theo id
    public Product getProductById(String id) {
        return products.get(id);
    }

    // Lấy toàn bộ sản phẩm (dạng Map)
    public Map<String, Product> getAllProducts() {
        return products;
    }
} 