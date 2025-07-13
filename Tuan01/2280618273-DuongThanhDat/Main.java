package Tuan01;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventoryManager = InventoryManager.getInstance();

    public static void main(String[] args) {
        int choice;
        do {
            displayMainMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1:
                    manageProducts();
                    break;
                case 2:
                    processOrder();
                    break;
                case 0:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println(); // New line for better readability
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("--- Retail Inventory Management System ---");
        System.out.println("1. Manage Products");
        System.out.println("2. Process Order");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }

    private static void manageProducts() {
        int choice;
        do {
            displayProductMenu();
            choice = getUserChoice();

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
                case 0:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }

    private static void displayProductMenu() {
        System.out.println("\n--- Product Management ---");
        System.out.println("1. Add New Product");
        System.out.println("2. Remove Product by ID");
        System.out.println("3. Update Product (Price/Quantity)");
        System.out.println("4. Search Products");
        System.out.println("5. Display All Products");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private static void addProduct() {
        System.out.println("\n--- Add New Product ---");
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity in Stock: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Select Product Type:");
        System.out.println("1. Electronic Product");
        System.out.println("2. Clothing Product");
        System.out.println("3. Food Product");
        System.out.print("Enter type choice: ");
        int typeChoice = getUserChoice();

        Product newProduct = null;
        switch (typeChoice) {
            case 1:
                System.out.print("Enter Warranty Period (months): ");
                int warranty = scanner.nextInt();
                System.out.print("Enter Power Consumption (Watts): ");
                double power = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                newProduct = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                System.out.print("Enter Size: ");
                String size = scanner.nextLine();
                System.out.print("Enter Material: ");
                String material = scanner.nextLine();
                newProduct = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                System.out.print("Enter Production Date (YYYY-MM-DD): ");
                LocalDate prodDate = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                LocalDate expDate = LocalDate.parse(scanner.nextLine());
                newProduct = new FoodProduct(id, name, price, quantity, prodDate, expDate);
                break;
            default:
                System.out.println("Invalid product type. Product not added.");
                return;
        }

        if (newProduct != null) {
            inventoryManager.addProduct(newProduct);
        }
    }

    private static void removeProduct() {
        System.out.println("\n--- Remove Product ---");
        System.out.print("Enter Product ID to remove: ");
        String id = scanner.nextLine();
        inventoryManager.removeProduct(id);
    }

    private static void updateProduct() {
        System.out.println("\n--- Update Product ---");
        System.out.print("Enter Product ID to update: ");
        String id = scanner.nextLine();
        Product product = inventoryManager.findProductById(id);
        if (product == null) {
            System.out.println("Product with ID " + id + " not found.");
            return;
        }

        System.out.print("Enter new Price (current: " + product.getPrice() + "): ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter new Quantity (current: " + product.getQuantityInStock() + "): ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        inventoryManager.updateProduct(id, newPrice, newQuantity);
    }

    private static void searchProducts() {
        System.out.println("\n--- Search Products ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Price Range");
        System.out.print("Enter search choice: ");
        int searchChoice = getUserChoice();

        List<Product> results;
        switch (searchChoice) {
            case 1:
                System.out.print("Enter product name (or part of it): ");
                String name = scanner.nextLine();
                results = inventoryManager.searchProductsByName(name);
                break;
            case 2:
                System.out.print("Enter minimum price: ");
                double minPrice = scanner.nextDouble();
                System.out.print("Enter maximum price: ");
                double maxPrice = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                results = inventoryManager.searchProductsByPriceRange(minPrice, maxPrice);
                break;
            default:
                System.out.println("Invalid search choice.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No products found matching your criteria.");
        } else {
            System.out.println("\n--- Search Results ---");
            results.forEach(Product::display);
            System.out.println("----------------------");
        }
    }

    private static void processOrder() {
        System.out.println("\n--- Process New Order ---");
        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        Order currentOrder = new Order(orderId);

        String addMore;
        do {
            System.out.print("Enter Product ID to add to order (or 'done' to finish): ");
            String productId = scanner.nextLine();
            if (productId.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                currentOrder.addItem(productId, quantity);
            } catch (OutOfStockException e) {
                System.out.println("Order Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Order Error: " + e.getMessage());
            }

            System.out.print("Add more items? (yes/no): ");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("yes"));

        currentOrder.displayOrderDetails();
    }
}
