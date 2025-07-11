import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

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
            System.out.println("Kho hàng trống.");
        } else {
            products.values().forEach(Product::display);
        }
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public double getTotalInventoryValue() {
        return products.values().stream().mapToDouble(p -> p.getPrice() * p.getQuantityInStock()).sum();
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new HashMap<>(products));
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            products = (Map<String, Product>) ois.readObject();
        }
    }

    public List<Product> getProductsSortedByPrice() {
        return products.values().stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .toList();
    }
} 