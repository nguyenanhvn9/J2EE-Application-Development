package Tuan01;

import java.util.*;

public class InventoryManager implements java.io.Serializable {
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

    public boolean removeProduct(String id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByPrice(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayAll() {
        for (Product p : products) {
            p.display();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}