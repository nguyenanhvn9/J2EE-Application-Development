package com.retail.inventory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class to manage inventory.
 */
public class InventoryManager implements Serializable {
    private static InventoryManager instance;
    private HashMap<String, Product> products;

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
        if (products.containsKey(product.getId()))
            throw new IllegalArgumentException("Product ID already exists.");
        products.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        if (!products.containsKey(id))
            throw new NoSuchElementException("Product ID not found.");
        products.remove(id);
    }

    public void updateProduct(String id, double price, int quantity) {
        Product p = products.get(id);
        if (p == null)
            throw new NoSuchElementException("Product ID not found.");
        if (price >= 0) p.setPrice(price);
        if (quantity >= 0) p.setQuantityInStock(quantity);
    }

    public List<Product> searchByName(String name) {
        String lower = name.toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        products.values().forEach(Product::display);
    }

    public double getTotalInventoryValue() {
        return products.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantityInStock())
                .sum();
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof HashMap) {
                products = (HashMap<String, Product>) obj;
            } else {
                throw new IOException("Invalid file format.");
            }
        }
    }
}
