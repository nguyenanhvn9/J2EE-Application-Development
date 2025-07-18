package managers;

import java.util.*;
import models.Product;

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
        products.put(product.id, product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public void updateQuantity(String id, int newQuantity) {
        if (products.containsKey(id)) {
            products.get(id).quantityInStock = newQuantity;
        }
    }

    public void displayAllProducts() {
        products.values().forEach(Product::display);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }
}