import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryManagerTest {
    @Test
    public void testAddAndRemoveProduct() {
        InventoryManager inventory = InventoryManager.getInstance();
        Product p = new ElectronicProduct("E1", "Laptop", 1000, 10, 12, 65);
        inventory.addProduct(p);
        assertEquals(p, inventory.getProductById("E1"));
        assertTrue(inventory.removeProduct("E1"));
        assertNull(inventory.getProductById("E1"));
    }

    @Test
    public void testUpdateProduct() {
        InventoryManager inventory = InventoryManager.getInstance();
        Product p = new ClothingProduct("C1", "T-Shirt", 200, 20, "L", "Cotton");
        inventory.addProduct(p);
        assertTrue(inventory.updateProduct("C1", 250, 15));
        Product updated = inventory.getProductById("C1");
        assertEquals(250, updated.getPrice());
        assertEquals(15, updated.getQuantityInStock());
        inventory.removeProduct("C1");
    }
} 