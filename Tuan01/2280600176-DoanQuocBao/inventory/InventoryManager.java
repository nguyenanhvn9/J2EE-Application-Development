package inventory;

import model.Product;
import java.util.*;

public class InventoryManager {
    private static InventoryManager instance = null;
    private Map<String, Product> products = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public Product findProductById(String id) {
        return products.get(id);
    }

    public List<Product> searchByName(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) result.add(p);
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) result.add(p);
        }
        return result;
    }

    public void updateProduct(String id, double price, int quantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(price);
            p.setQuantityInStock(quantity);
        }
    }

    public void showAllProducts() {
        for (Product p : products.values()) {
            p.display();
        }
    }
}
