import java.util.ArrayList;
import java.util.List;

class InventoryManager {
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

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(int id) {
        products.removeIf(product -> product.id == id);
    }

    public void updateProduct(int id, double price, int quantity) {
        for (Product product : products) {
            if (product.id == id) {
                product.price = price;
                product.quantityInStock = quantity;
            }
        }
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.name.equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayAllProducts() {
        for (Product product : products) {
            product.display();
        }
    }
}
