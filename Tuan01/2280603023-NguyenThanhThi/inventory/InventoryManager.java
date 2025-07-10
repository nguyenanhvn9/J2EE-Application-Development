package Tuan01;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> inventory;

    private InventoryManager() {
        inventory = new HashMap<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
        System.out.println("Product added successfully: " + product.getName());
    }

    public void removeProduct(String id) {
        if (inventory.remove(id) != null) {
            System.out.println("Product removed successfully: ID=" + id);
        } else {
            System.out.println("Product not found: ID=" + id);
        }
    }

    public void updateProduct(String id, double price, int quantity) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantityInStock(quantity);
            System.out.println("Product updated successfully: ID=" + id);
        } else {
            System.out.println("Product not found: ID=" + id);
        }
    }

    public List<Product> searchByName(String name) {
        return inventory.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        return inventory.values().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void displayAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            inventory.values().forEach(Product::display);
        }
    }

    public Product getProduct(String id) {
        return inventory.get(id);
    }

    public double getTotalInventoryValue() {
        return inventory.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantityInStock())
                .sum();
    }
}
