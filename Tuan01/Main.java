package Tuan01;

import java.util.Scanner;

public class Main {
    private static InventoryManager inventory = InventoryManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== Retail Inventory Management ====");
            System.out.println("1. Add product");
            System.out.println("2. Remove product by ID");
            System.out.println("3. Update product");
            System.out.println("4. Search products by name");
            System.out.println("5. Search products by price range");
            System.out.println("6. Display all products");
            System.out.println("7. Create order");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addProduct(); break;
                case 2: removeProduct(); break;
                case 3: updateProduct(); break;
                case 4: searchByName(); break;
                case 5: searchByPrice(); break;
                case 6: inventory.displayAllProducts(); break;
                case 7: createOrder(); break;
                case 0: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Type (1=Electronic, 2=Clothing, 3=Food): ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantity: "); int qty = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                System.out.print("Warranty (months): ");
                int w = Integer.parseInt(scanner.nextLine());
                System.out.print("Power (W): ");
                double power = Double.parseDouble(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, qty, w, power));
                break;
            case 2:
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Material: ");
                String mat = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, qty, size, mat));
                break;
            case 3:
                System.out.print("Manufacture Date: ");
                String mfg = scanner.nextLine();
                System.out.print("Expiry Date: ");
                String exp = scanner.nextLine();
                inventory.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
                break;
        }
    }

    private static void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine();
        inventory.removeProductById(id);
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        String id = scanner.nextLine();
        System.out.print("New price: ");
double price = Double.parseDouble(scanner.nextLine());
        System.out.print("New quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());
        inventory.updateProduct(id, price, qty);
    }

    private static void searchByName() {
        System.out.print("Enter name keyword: ");
        String name = scanner.nextLine();
        for (Product p : inventory.searchByName(name)) {
            p.display();
        }
    }

    private static void searchByPrice() {
        System.out.print("Min price: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Max price: ");
        double max = Double.parseDouble(scanner.nextLine());
        for (Product p : inventory.searchByPriceRange(min, max)) {
            p.display();
        }
    }

    private static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Enter product ID to add to order (or 'done'): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(id);
            if (p == null) {
                System.out.println("Product not found.");
                continue;
            }
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addItem(p, qty);
            } catch (OutOfStockException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        order.displayOrder();
    }
}

