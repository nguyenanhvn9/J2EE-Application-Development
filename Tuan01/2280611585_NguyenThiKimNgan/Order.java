package Tuan01;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(String productid, int quantity) throws OutOfStockException{
        InventoryManager inventoryManager = InventoryManager.getInstance();

        Product product = inventoryManager.findProductById(productid);
        if(product == null) {
            System.out.println("Khong tim thay san pham voi ID: " + productid);
            return;
        }
        if(product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("San pham " + product.getName() + " khong du so luong trong kho.");
        }

        product.quantityInStock -= quantity;

        OrderItem item = new OrderItem(product, quantity);
        items.add(item);
        System.out.println("Da them san pham " + product.getName() + " voi so luong " + quantity + " vao don hang.");

    }
    
     public void displayOrder() {
        if (items.isEmpty()) {
            System.out.println("Don hang hien tai khong co san pham nao.");
            return;
        }

        System.out.println("Chi tiet don hang:");
        double total = 0;

        for (OrderItem item : items) {
            item.displayInfo();
            total += item.getTotalPrice();
        }

        System.out.println("Tong tien: " + total);
    }

    public List<OrderItem> getItems() {
        return items;
    }


}
