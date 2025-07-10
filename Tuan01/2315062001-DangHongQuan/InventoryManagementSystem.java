import java.util.Scanner;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter product type (Electronic/Clothing/Food): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter quantity in stock: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (type.equalsIgnoreCase("Electronic")) {
                        System.out.print("Enter warranty months: ");
                        int warranty = scanner.nextInt();
                        System.out.print("Enter power: ");
                        double power = scanner.nextDouble();
                        manager.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                    } else if (type.equalsIgnoreCase("Clothing")) {
                        System.out.print("Enter size: ");
                        String size = scanner.nextLine();
                        System.out.print("Enter material: ");
                        String material = scanner.nextLine();
                        manager.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                    } else if (type.equalsIgnoreCase("Food")) {
                        System.out.print("Enter production date: ");
                        String productionDate = scanner.nextLine();
                        System.out.print("Enter expiration date: ");
                        String expirationDate = scanner.nextLine();
                        manager.addProduct(new FoodProduct(id, name, price, quantity, productionDate, expirationDate));
                    } else {
                        System.out.println("Invalid product type.");
                    }
                    break;

                case 2:
                    manager.displayAllProducts();
                    break;

                case 3:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
