package Tuan01;

import java.time.LocalDate;
import java.util.Scanner;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Retail Inventory Management System ===");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Products by Name");
            System.out.println("5. Search Products by Price Range");
            System.out.println("6. Display All Products");
            System.out.println("7. Create Order");
            System.out.println("8. Show Total Inventory Value");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Select product type: 1. Electronic 2. Clothing 3. Food");
                    int type = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    try {
                        if (type == 1) {
                            System.out.print("Enter Warranty Months: ");
                            int warranty = Integer.parseInt(scanner.nextLine());
                            System.out.print("Enter Power Consumption (W): ");
                            double power = Double.parseDouble(scanner.nextLine());
                            inventoryManager.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                        } else if (type == 2) {
                            System.out.print("Enter Size: ");
                            String size = scanner.nextLine();
                            System.out.print("Enter Material: ");
                            String material = scanner.nextLine();
                            inventoryManager.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                        } else if (type == 3) {
                            System.out.print("Enter Manufacture Date (YYYY-MM-DD): ");
                            LocalDate manufactureDate = LocalDate.parse(scanner.nextLine());
                            System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                            LocalDate expiryDate = LocalDate.parse(scanner.nextLine());
                            inventoryManager.addProduct(new FoodProduct(id, name, price, quantity, manufactureDate, expiryDate));
                        } else {
                            System.out.println("Invalid product type.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    inventoryManager.removeProduct(scanner.nextLine());
                    break;

                case 3:
                    System.out.print("Enter Product ID: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter New Quantity: ");
                    int newQuantity = Integer.parseInt(scanner.nextLine());
                    inventoryManager.updateProduct(updateId, newPrice, newQuantity);
                    break;

                case 4:
                    System.out.print("Enter Product Name to search: ");
                    String searchName = scanner.nextLine();
                    inventoryManager.searchByName(searchName).forEach(Product::display);
                    break;

                case 5:
                    System.out.print("Enter Minimum Price: ");
                    double minPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter Maximum Price: ");
                    double maxPrice = Double.parseDouble(scanner.nextLine());
                    inventoryManager.searchByPriceRange(minPrice, maxPrice).forEach(Product::display);
                    break;

                case 6:
                    inventoryManager.displayAllProducts();
                    break;

                case 7:
                    System.out.print("Enter Order ID: ");
                    String orderId = scanner.nextLine();
                    Order order = new Order(orderId);
                    while (true) {
                        System.out.print("Enter Product ID to add to order (or 'done' to finish): ");
                        String productId = scanner.nextLine();
                        if (productId.equalsIgnoreCase("done")) break;
                        System.out.print("Enter Quantity: ");
                        int orderQuantity = Integer.parseInt(scanner.nextLine());
                        Product product = inventoryManager.getProduct(productId);
                        if (product != null) {
                            try {
                                order.addItem(product, orderQuantity);
                            } catch (OutOfStockException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Product not found: ID=" + productId);
                        }
                    }
                    order.displayOrder();
                    break;

                case 8:
                    System.out.printf("Total Inventory Value: %.2f%n", inventoryManager.getTotalInventoryValue());
                    break;

                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
