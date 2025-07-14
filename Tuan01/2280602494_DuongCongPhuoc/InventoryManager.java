import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;

    private InventoryManager() {
        products = new HashMap<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public void updateProduct(String id, double price, int quantity) {
        Product product = products.get(id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantityInStock(quantity);
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                result.add(product);
            }
        }
        return result;
    }

    public void displayAllProducts() {
        for (Product product : products.values()) {
            product.display();
        }
    }

    // Thêm phương thức để lấy sản phẩm theo ID
    public Product getProductById(String productId) {
        return products.get(productId);
    }
}