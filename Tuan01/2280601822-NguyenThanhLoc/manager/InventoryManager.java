package manager;

import model.Product;
import java.util.*;
import java.util.stream.Collectors;

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

    public boolean removeProduct(String id) {
        return products.remove(id) != null;
    }

    public boolean updateProduct(String id, double price, int quantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(price);
            p.setQuantityInStock(quantity);
            return true;
        }
        return false;
    }

    public List<Product> searchByName(String name) {
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(double min, double max) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Kho hàng trống!");
        } else {
            products.values().forEach(Product::display);
        }
    }

    public Product getProductById(String id) {
        return products.get(id);
    }
} 