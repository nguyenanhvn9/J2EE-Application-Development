package com.retail.inventory.manager;

import com.retail.inventory.model.*;
import com.retail.inventory.exception.OutOfStockException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;
    private List<Order> orders;
    private static final String DATA_FILE = "inventory_data.dat";

    private InventoryManager() {
        products = new HashMap<>();
        orders = new ArrayList<>();
        loadData();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Product Management
    public void addProduct(Product product) {
        products.put(product.getId(), product);
        saveData();
        System.out.printf("✅ Product added successfully: %s\n", product.getName());
    }

    public boolean removeProduct(String id) {
        Product removed = products.remove(id);
        if (removed != null) {
            saveData();
            System.out.printf("✅ Product removed successfully: %s\n", removed.getName());
            return true;
        }
        System.out.printf("❌ Product not found with ID: %s\n", id);
        return false;
    }

    public void updateProduct(String id, double newPrice, int newQuantity) {
        Product product = products.get(id);
        if (product != null) {
            product.setPrice(newPrice);
            product.setQuantityInStock(newQuantity);
            saveData();
            System.out.printf("✅ Product updated successfully: %s\n", product.getName());
        } else {
            System.out.printf("❌ Product not found with ID: %s\n", id);
        }
    }

    public List<Product> searchByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        return products.values().stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("📭 No products in inventory.");
            return;
        }

        System.out.printf("\n🏪 INVENTORY OVERVIEW\n");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("📊 Total Products: %d\n", products.size());
        System.out.printf("💰 Total Value: $%.2f\n", calculateTotalInventoryValue());
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        products.values().forEach(Product::display);
    }

    // Order Processing
    public Order createOrder(String orderId, Map<String, Integer> productQuantities)
            throws OutOfStockException {
        Order order = new Order(orderId);

        // Check stock availability first
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            Product product = products.get(entry.getKey());
            if (product == null) {
                throw new OutOfStockException("Product not found: " + entry.getKey());
            }
            if (product.getQuantityInStock() < entry.getValue()) {
                throw new OutOfStockException("Insufficient stock for product: " + product.getName() +
                        ". Available: " + product.getQuantityInStock() + ", Requested: " + entry.getValue());
            }
        }

        // If all products are available, create the order
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            Product product = products.get(entry.getKey());
            order.addProduct(product, entry.getValue());
            // Update stock
            product.setQuantityInStock(product.getQuantityInStock() - entry.getValue());
        }

        orders.add(order);
        saveData();
        System.out.printf("✅ Order created successfully: %s\n", orderId);
        return order;
    }

    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("📭 No orders found.");
            return;
        }

        System.out.printf("\n🛒 ORDER HISTORY\n");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("📊 Total Orders: %d\n", orders.size());
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        orders.forEach(Order::displayOrder);
    }

    // Utility Methods
    private double calculateTotalInventoryValue() {
        return products.values().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantityInStock())
                .sum();
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    // Data Persistence
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(products);
            oos.writeObject(orders);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            products = (Map<String, Product>) ois.readObject();
            orders = (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // File doesn't exist or is corrupted, start with empty data
            products = new HashMap<>();
            orders = new ArrayList<>();
        }
    }
}