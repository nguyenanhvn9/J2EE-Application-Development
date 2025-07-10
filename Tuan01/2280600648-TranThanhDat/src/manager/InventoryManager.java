/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;
import model.Product;
import java.util.*;

/**
 *
 * @author DatG
 */
public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products = new ArrayList<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public boolean removeProduct(String id) {
        return products.removeIf(p -> p.getId().equalsIgnoreCase(id));
    }

    public void updateQuantity(String id, int newQty) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                p.setQuantityInStock(newQty);
                System.out.println("✔ Cập nhật số lượng thành công cho sản phẩm có ID: " + id);
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sản phẩm có ID: " + id);
    }

    public List<Product> searchByName(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("📦 Kho hàng đang trống.");
        } else {
            System.out.println("📋 Danh sách sản phẩm trong kho:");
            for (Product p : products) {
                p.display();
                System.out.println("-----------------------------");
            }
        }
    }
    public Product findById(String id) {
    for (Product p : products) {
        if (p.getId().equalsIgnoreCase(id)) {
            return p;
        }
    }
    return null;
}

}
