package Tuan01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;

    private InventoryManager() {
        products = new HashMap<>();
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        if (products.containsKey(product.getId())) {
            System.out.println("Error: Product with ID " + product.getId() + " already exists.");
        } else {
            products.put(product.getId(), product);
            System.out.println("Product " + product.getName() + " added successfully.");
        }
    }

    public void removeProduct(String id) {
        if (products.containsKey(id)) {
            Product removedProduct = products.remove(id);
            System.out.println("Product " + removedProduct.getName() + " removed successfully.");
        } else {
            System.out.println("Error: Product with ID " + id + " not found.");
        }
    }

    public void updateProduct(String id, double newPrice, int newQuantity) {
        Product product = products.get(id);
        if (product != null) {
            product.setPrice(newPrice);
            product.setQuantityInStock(newQuantity);
            System.out.println("Product " + product.getName() + " updated successfully.");
        }
        else {
            System.out.println("Error: Product with ID " + id + " not found.");
        }
    }

    public Product findProductById(String id) {
        return products.get(id);
    }

    public List<Product> searchProductsByName(String name) {
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchProductsByPriceRange(double minPrice, double maxPrice) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n--- Current Inventory ---");
        products.values().forEach(Product::display);
        System.out.println("-------------------------");
    }

    public Map<String, Product> getProducts() {
        return products;
    }
}