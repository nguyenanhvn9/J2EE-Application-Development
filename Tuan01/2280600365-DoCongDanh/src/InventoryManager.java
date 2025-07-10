import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;
    private static final String FILE_NAME = "products.dat";

    private InventoryManager() {
        products = new HashMap<>();
        loadFromFile();
    }

    public static InventoryManager getInstance() {
        if (instance == null)
            instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.id, product);
        saveToFile();
    }

    public void removeProduct(String id) {
        products.remove(id);
        saveToFile();
    }

    public void updateProduct(String id, double newPrice, int newQuantity) {
        Product product = products.get(id);
        if (product != null) {
            product.price = newPrice;
            product.quantityInStock = newQuantity;
            saveToFile();
        }
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

    public Product getProduct(String id) {
        return products.get(id);
    }

    public void displayAll() {
        products.values().forEach(Product::display);
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                products = (Map<String, Product>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Loi khi doc file: " + e.getMessage());
            }
        }
    }
}