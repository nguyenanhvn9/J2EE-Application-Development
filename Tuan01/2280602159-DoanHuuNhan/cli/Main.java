package cli;

import model.*;
import manager.InventoryManager;
import order.Order;
import exception.OutOfStockException;
import java.util.*;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProductCLI(); break;
                case 2: removeProductCLI(); break;
                case 3: updateProductCLI(); break;
                case 4: searchProductCLI(); break;
                case 5: inventory.displayAll(); break;
                case 6: createOrderCLI(); break;
                case 7: saveLoadCLI(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
    }
    private static void printMenu() {
        System.out.println("\n1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Update Product");
        System.out.println("4. Search Product");
        System.out.println("5. Display All Products");
        System.out.println("6. Create Order");
        System.out.println("7. Save/Load Inventory");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }
    private static void addProductCLI() {
        System.out.println("Product type (1-Electronic, 2-Clothing, 3-Food): ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantity: "); int qty = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                System.out.print("Warranty (months): "); int wm = Integer.parseInt(scanner.nextLine());
                System.out.print("Power (W): "); double pw = Double.parseDouble(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, qty, wm, pw));
                break;
            case 2:
                System.out.print("Size: "); String size = scanner.nextLine();
                System.out.print("Material: "); String mat = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, qty, size, mat));
                break;
            case 3:
                System.out.print("Production date (yyyy-mm-dd): "); LocalDate pd = LocalDate.parse(scanner.nextLine());
                System.out.print("Expiry date (yyyy-mm-dd): "); LocalDate ed = LocalDate.parse(scanner.nextLine());
                inventory.addProduct(new FoodProduct(id, name, price, qty, pd, ed));
                break;
            default:
                System.out.println("Invalid type");
        }
    }
    private static void removeProductCLI() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine();
        if (inventory.removeProduct(id)) System.out.println("Removed.");
        else System.out.println("Not found.");
    }
    private static void updateProductCLI() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();
        System.out.print("New price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("New quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());
        if (inventory.updateProduct(id, price, qty)) System.out.println("Updated.");
        else System.out.println("Not found.");
    }
    private static void searchProductCLI() {
        System.out.println("1. By name\n2. By price range");
        int ch = Integer.parseInt(scanner.nextLine());
        if (ch == 1) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            inventory.searchByName(name).forEach(Product::display);
        } else {
            System.out.print("Min price: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Max price: ");
            double max = Double.parseDouble(scanner.nextLine());
            inventory.searchByPriceRange(min, max).forEach(Product::display);
        }
    }
    private static void createOrderCLI() {
        Order order = new Order();
        while (true) {
            System.out.print("Enter product ID (or 'done'): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(id);
            if (p == null) {
                System.out.println("Not found.");
                continue;
            }
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addProduct(p, qty);
                System.out.println("Added to order.");
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.displayOrder();
    }
    private static void saveLoadCLI() {
        System.out.println("1. Save\n2. Load");
        int ch = Integer.parseInt(scanner.nextLine());
        System.out.print("Filename: ");
        String fn = scanner.nextLine();
        try {
            if (ch == 1) inventory.saveToFile(fn);
            else inventory.loadFromFile(fn);
            System.out.println("Done.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
