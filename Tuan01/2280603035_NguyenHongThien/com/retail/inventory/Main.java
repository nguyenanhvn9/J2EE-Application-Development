package com.retail.inventory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Main class with CLI for Retail Inventory Management System.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addProductCLI(); break;
                    case "2": removeProductCLI(); break;
                    case "3": updateProductCLI(); break;
                    case "4": searchByNameCLI(); break;
                    case "5": searchByPriceRangeCLI(); break;
                    case "6": inventory.displayAllProducts(); break;
                    case "7": createAndProcessOrderCLI(); break;
                    case "8": saveToFileCLI(); break;
                    case "9": loadFromFileCLI(); break;
                    case "0": System.out.println("Exiting..."); return;
                    default: System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Retail Inventory Management System ===");
        System.out.println("1. Add a new product");
        System.out.println("2. Remove a product by ID");
        System.out.println("3. Update product price/quantity");
        System.out.println("4. Search products by name");
        System.out.println("5. Search products by price range");
        System.out.println("6. Display all products");
        System.out.println("7. Create and process a new order");
        System.out.println("8. Save inventory to file");
        System.out.println("9. Load inventory from file");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addProductCLI() {
        System.out.println("Select product type:");
        System.out.println("1. Electronic");
        System.out.println("2. Clothing");
        System.out.println("3. Food");
        System.out.print("Choice: ");
        String type = scanner.nextLine().trim();
        try {
            System.out.print("Enter product ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();
            double price = promptDouble("Enter price: ");
            int quantity = promptInt("Enter quantity in stock: ");
            Product product = null;
            switch (type) {
                case "1":
                    int warranty = promptInt("Enter warranty months: ");
                    double power = promptDouble("Enter power consumption (W): ");
                    product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                    break;
                case "2":
                    System.out.print("Enter size: ");
                    String size = scanner.nextLine().trim();
                    System.out.print("Enter material: ");
                    String material = scanner.nextLine().trim();
                    product = new ClothingProduct(id, name, price, quantity, size, material);
                    break;
                case "3":
                    LocalDate prodDate = promptDate("Enter production date (yyyy-MM-dd): ");
                    LocalDate expDate = promptDate("Enter expiration date (yyyy-MM-dd): ");
                    product = new FoodProduct(id, name, price, quantity, prodDate, expDate);
                    break;
                default:
                    System.out.println("Invalid product type.");
                    return;
            }
            inventory.addProduct(product);
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add product: " + e.getMessage());
        }
    }

    private static void removeProductCLI() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine().trim();
        inventory.removeProduct(id);
        System.out.println("Product removed.");
    }

    private static void updateProductCLI() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine().trim();
        double price = promptDouble("Enter new price (or -1 to skip): ");
        int quantity = promptInt("Enter new quantity (or -1 to skip): ");
        inventory.updateProduct(id, price >= 0 ? price : -1, quantity >= 0 ? quantity : -1);
        System.out.println("Product updated.");
    }

    private static void searchByNameCLI() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        List<Product> results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("No products found.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void searchByPriceRangeCLI() {
        double min = promptDouble("Enter min price: ");
        double max = promptDouble("Enter max price: ");
        List<Product> results = inventory.searchByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("No products found.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void createAndProcessOrderCLI() {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId);
        while (true) {
            System.out.print("Enter product ID to add to order (or 'done' to finish): ");
            String pid = scanner.nextLine().trim();
            if (pid.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(pid);
            if (p == null) {
                System.out.println("Product not found.");
                continue;
            }
            int qty = promptInt("Enter quantity: ");
            try {
                order.addProduct(p, qty);
                System.out.println("Added to order.");
            } catch (OutOfStockException | IllegalArgumentException e) {
                System.out.println("Cannot add: " + e.getMessage());
            }
        }
        try {
            order.processOrder(inventory);
            System.out.println("Order processed successfully.");
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("Order failed: " + e.getMessage());
        }
    }

    private static void saveToFileCLI() {
        System.out.print("Enter filename to save: ");
        String filename = scanner.nextLine().trim();
        try {
            inventory.saveToFile(filename);
            System.out.println("Inventory saved.");
        } catch (Exception e) {
            System.out.println("Failed to save: " + e.getMessage());
        }
    }

    private static void loadFromFileCLI() {
        System.out.print("Enter filename to load: ");
        String filename = scanner.nextLine().trim();
        try {
            inventory.loadFromFile(filename);
            System.out.println("Inventory loaded.");
        } catch (Exception e) {
            System.out.println("Failed to load: " + e.getMessage());
        }
    }

    // Utility input methods
    private static double promptDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) throw new NumberFormatException();
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a non-negative number.");
            }
        }
    }

    private static int promptInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val < 0) throw new NumberFormatException();
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a non-negative integer.");
            }
        }
    }

    private static LocalDate promptDate(String prompt) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), fmt);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            }
        }
    }
}
