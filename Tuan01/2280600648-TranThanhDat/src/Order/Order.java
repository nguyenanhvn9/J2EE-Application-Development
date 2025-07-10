/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Order;
import model.Product;
import manager.InventoryManager;
import java.util.*;

/**
 *
 * @author DatG
 */
public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product p, int quantity) throws OutOfStockException {
        if (p.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Không đủ hàng: " + p.getName());
        }
        items.put(p, quantity);
        p.setQuantityInStock(p.getQuantityInStock() - quantity);
    }

    public void showOrder() {
        for (var entry : items.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
    }
}
