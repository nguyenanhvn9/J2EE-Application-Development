import java.util.*;

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

    // Them san pham, khong cho trung Id
    public boolean addProduct(Product product) {
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                System.out.println("Khong the them, ID da ton tai!");
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean removeProductById(String id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    public boolean updateProduct(String id, double newPrice, int newQuantity) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setPrice(newPrice);
                p.setQuantityInStock(newQuantity);
                return true;
            }
        }
        return false;
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

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Kho hang trong!");
        } else {
            for (Product p : products) {
                p.display();
            }
        }
    }

    // Lay san pham theo Id, neu khong ton tai thi nem ProductNotFoundException
    public Product getProductById(String id) throws ProductNotFoundException {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("San pham voi ID " + id + " khong ton tai!");
    }

    public List<Product> getProducts() {
        return products;
    }
} 