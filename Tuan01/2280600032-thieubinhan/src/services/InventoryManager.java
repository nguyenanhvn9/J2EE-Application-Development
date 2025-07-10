package services;

import entity.Product;

import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;

    private InventoryManager() {
        products = new HashMap<>();
    }

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

    public void updateProduct(String id, double newPrice, int newQuantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice( newPrice);
            p.setQuantityInStock(newQuantity);
        }
    }

    public List<Product> searchByName(String keyword) {
        List<Product> results = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> results = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                results.add(p);
            }
        }
        return results;
    }

    public void displayAll() {
        for (Product p : products.values()) {
            p.display();
        }
    }

    public Product getProductById(String id) {
        return products.get(id);
    }
}
