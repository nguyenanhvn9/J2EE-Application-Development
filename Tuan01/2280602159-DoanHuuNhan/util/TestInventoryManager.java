package util;

import manager.InventoryManager;
import model.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class TestInventoryManager {
    @Test
    public void testAddAndRemoveProduct() {
        InventoryManager inv = InventoryManager.getInstance();
        Product p = new ElectronicProduct("E1", "Laptop", 1000, 10, 24, 65);
        inv.addProduct(p);
        assertTrue(inv.getProducts().contains(p));
        inv.removeProduct("E1");
        assertFalse(inv.getProducts().contains(p));
    }
    @Test
    public void testUpdateProduct() {
        InventoryManager inv = InventoryManager.getInstance();
        Product p = new ClothingProduct("C1", "Shirt", 20, 50, "M", "Cotton");
        inv.addProduct(p);
        inv.updateProduct("C1", 25, 40);
        assertEquals(25, p.getPrice());
        assertEquals(40, p.getQuantityInStock());
        inv.removeProduct("C1");
    }
    @Test
    public void testSearchByName() {
        InventoryManager inv = InventoryManager.getInstance();
        Product p = new FoodProduct("F1", "Milk", 2, 100, LocalDate.now(), LocalDate.now().plusDays(7));
        inv.addProduct(p);
        assertFalse(inv.searchByName("Bread").contains(p));
        assertTrue(inv.searchByName("Milk").contains(p));
        inv.removeProduct("F1");
    }
}
