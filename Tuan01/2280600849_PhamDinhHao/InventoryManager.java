import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

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
        return products.removeIf(p -> p.getId().equals(id));
    }

    public boolean updateProduct(String id, double price, int quantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setPrice(price);
                p.setQuantityInStock(quantity);
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

    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            for (Product p : products) {
                p.display();
            }
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            products = (List<Product>) ois.readObject();
        }
    }

    public double getTotalInventoryValue() {
        return products.stream().mapToDouble(p -> p.getPrice() * p.getQuantityInStock()).sum();
    }

    public List<Product> getLowStockProducts(int threshold) {
        return products.stream().filter(p -> p.getQuantityInStock() < threshold).collect(Collectors.toList());
    }
} 