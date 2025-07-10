package Tuan01.RetailInventorySystem.manager;

import Tuan01.RetailInventorySystem.model.Product;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        products = new ArrayList<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public boolean removeProductById(String id) {
        return products.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public boolean updateProduct(String id, double newPrice, int newQuantity) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                p.setPrice(newPrice);
                p.setQuantityInStock(newQuantity);
                return true;
            }
        }
        return false;
    }

    public List<Product> searchByName(String name) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPriceRange(double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Kho hàng trống.");
        } else {
            for (Product p : products) {
                p.display();
            }
        }
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }
}
