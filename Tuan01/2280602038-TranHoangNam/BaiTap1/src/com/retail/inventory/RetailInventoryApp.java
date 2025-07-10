package com.retail.inventory;

import com.retail.inventory.manager.InventoryManager;
import com.retail.inventory.model.*;
import com.retail.inventory.exception.OutOfStockException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class RetailInventoryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InventoryManager inventoryManager = InventoryManager.getInstance();

    public static void main(String[] args) {
        System.out.println("🏪 Welcome to Retail Inventory Management System!");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Add some sample data
        addSampleData();

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    productManagementMenu();
                    break;
                case 2:
                    orderManagementMenu();
                    break;
                case 3:
                    System.out.println("👋 Thank you for using Retail Inventory Management System!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n🏠 MAIN MENU");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 📦 Product Management");
        System.out.println("2. 🛒 Order Management");
        System.out.println("3. 🚪 Exit");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private static void productManagementMenu() {
        while (true) {
            System.out.println("\n📦 PRODUCT MANAGEMENT");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("1. ➕ Add Product");
            System.out.println("2. ❌ Remove Product");
            System.out.println("3. ✏️  Update Product");
            System.out.println("4. 🔍 Search Products");
            System.out.println("5. 📋 Display All Products");
            System.out.println("6. 🔙 Back to Main Menu");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    searchProducts();
                    break;
                case 5:
                    inventoryManager.displayAllProducts();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.println("\n➕ ADD NEW PRODUCT");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 📱 Electronic Product");
        System.out.println("2. 👕 Clothing Product");
        System.out.println("3. 🍎 Food Product");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int type = getIntInput("Select product type: ");

        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        double price = getDoubleInput("Enter price: $");
        int quantity = getIntInput("Enter quantity in stock: ");

        Product product = null;

        switch (type) {
            case 1:
                int warranty = getIntInput("Enter warranty (months): ");
                double power = getDoubleInput("Enter power consumption (W): ");
                product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                System.out.print("Enter size: ");
                String size = scanner.nextLine();
                System.out.print("Enter material: ");
                String material = scanner.nextLine();
                product = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                LocalDate mfgDate = getDateInput("Enter manufacturing date (dd/MM/yyyy): ");
                LocalDate expDate = getDateInput("Enter expiration date (dd/MM/yyyy): ");
                product = new FoodProduct(id, name, price, quantity, mfgDate, expDate);
                break;
            default:
                System.out.println("❌ Invalid product type.");
                return;
        }

        if (product != null) {
            inventoryManager.addProduct(product);
        }
    }

    private static void removeProduct() {
        System.out.print("\n❌ Enter product ID to remove: ");
        String id = scanner.nextLine();
        inventoryManager.removeProduct(id);
    }

    private static void updateProduct() {
        System.out.print("\n✏️  Enter product ID to update: ");
        String id = scanner.nextLine();

        Product product = inventoryManager.getProductById(id);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }

        System.out.printf("Current price: $%.2f\n", product.getPrice());
        double newPrice = getDoubleInput("Enter new price: $");

        System.out.printf("Current quantity: %d\n", product.getQuantityInStock());
        int newQuantity = getIntInput("Enter new quantity: ");

        inventoryManager.updateProduct(id, newPrice, newQuantity);
    }

    private static void searchProducts() {
        System.out.println("\n🔍 SEARCH PRODUCTS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1. 📝 Search by name");
        System.out.println("2. 💰 Search by price range");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        int choice = getIntInput("Choose search type: ");

        switch (choice) {
            case 1:
                System.out.print("Enter product name to search: ");
                String name = scanner.nextLine();
                List<Product> nameResults = inventoryManager.searchByName(name);
                displaySearchResults(nameResults);
                break;
            case 2:
                double minPrice = getDoubleInput("Enter minimum price: $");
                double maxPrice = getDoubleInput("Enter maximum price: $");
                List<Product> priceResults = inventoryManager.searchByPriceRange(minPrice, maxPrice);
                displaySearchResults(priceResults);
                break;
            default:
                System.out.println("❌ Invalid search type.");
        }
    }

    private static void displaySearchResults(List<Product> results) {
        if (results.isEmpty()) {
            System.out.println("🔍 No products found matching your criteria.");
            return;
        }

        System.out.printf("\n🔍 SEARCH RESULTS (%d products found)\n", results.size());
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        results.forEach(Product::display);
    }

    private static void orderManagementMenu() {
        while (true) {
            System.out.println("\n🛒 ORDER MANAGEMENT");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("1. 🆕 Create New Order");
            System.out.println("2. 📋 Display All Orders");
            System.out.println("3. 🔙 Back to Main Menu");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    inventoryManager.displayAllOrders();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }

    private static void createOrder() {
        System.out.print("\n🆕 Enter order ID: ");
        String orderId = scanner.nextLine();

        Map<String, Integer> productQuantities = new HashMap<>();

        System.out.println("\n📋 Available Products:");
        inventoryManager.getAllProducts().forEach(product -> {
            System.out.printf("• %s (ID: %s) - Stock: %d - Price: $%.2f\n",
                    product.getName(), product.getId(), product.getQuantityInStock(), product.getPrice());
        });

        System.out.println("\n🛍️ Add products to order (enter 'done' when finished):");

        while (true) {
            System.out.print("Enter product ID (or 'done' to finish): ");
            String productId = scanner.nextLine();

            if ("done".equalsIgnoreCase(productId)) {
                break;
            }

            Product product = inventoryManager.getProductById(productId);
            if (product == null) {
                System.out.println("❌ Product not found. Please try again.");
                continue;
            }

            int quantity = getIntInput("Enter quantity: ");
            if (quantity <= 0) {
                System.out.println("❌ Quantity must be positive.");
                continue;
            }

            productQuantities.put(productId, quantity);
            System.out.printf("✅ Added %d units of %s to order.\n", quantity, product.getName());
        }

        if (productQuantities.isEmpty()) {
            System.out.println("❌ No products added to order.");
            return;
        }

        try {
            Order order = inventoryManager.createOrder(orderId, productQuantities);
            System.out.println("\n🎉 Order created successfully!");
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("❌ Order creation failed: " + e.getMessage());
        }
    }

    private static void addSampleData() {
        // Add sample electronic products
        inventoryManager.addProduct(new ElectronicProduct("E001", "iPhone 14 Pro", 999.99, 50, 24, 3.5));
        inventoryManager.addProduct(new ElectronicProduct("E002", "Samsung Galaxy S23", 899.99, 30, 24, 3.2));
        inventoryManager.addProduct(new ElectronicProduct("E003", "MacBook Pro 16\"", 2499.99, 10, 12, 140.0));

        // Add sample clothing products
        inventoryManager.addProduct(new ClothingProduct("C001", "Nike Air Max Sneakers", 129.99, 100, "US 9", "Synthetic Leather"));
        inventoryManager.addProduct(new ClothingProduct("C002", "Levi's 501 Jeans", 79.99, 75, "32x32", "100% Cotton"));
        inventoryManager.addProduct(new ClothingProduct("C003", "Adidas T-Shirt", 29.99, 200, "L", "Cotton Blend"));

        // Add sample food products
        inventoryManager.addProduct(new FoodProduct("F001", "Organic Honey", 12.99, 50,
                LocalDate.of(2024, 1, 15), LocalDate.of(2026, 1, 15)));
        inventoryManager.addProduct(new FoodProduct("F002", "Premium Coffee Beans", 24.99, 80,
                LocalDate.of(2024, 6, 1), LocalDate.of(2025, 6, 1)));
        inventoryManager.addProduct(new FoodProduct("F003", "Expired Milk", 3.99, 10,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 15))); // Expired product for demonstration
    }

    // Utility methods for input validation
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Please enter date in format dd/MM/yyyy.");
            }
        }
    }
}
