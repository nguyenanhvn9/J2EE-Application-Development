import java.util.*;
import model.*;
import inventory.InventoryManager;
import exception.OutOfStockException;
import order.Order;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventory = InventoryManager.getInstance();

        while (true) {
            System.out.println("\n==== RETAIL INVENTORY SYSTEM ====");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product");
            System.out.println("4. Search Product");
            System.out.println("5. Display All");
            System.out.println("6. Create Order");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter type (electronic/clothing/food): ");
                    String type = scanner.nextLine();
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("Name: "); String name = scanner.nextLine();
                    System.out.print("Price: "); double price = scanner.nextDouble();
                    System.out.print("Quantity: "); int qty = scanner.nextInt(); scanner.nextLine();
                    if (type.equals("electronic")) {
                        System.out.print("Warranty (months): "); int warranty = scanner.nextInt();
                        System.out.print("Power (W): "); int power = scanner.nextInt(); scanner.nextLine();
                        inventory.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
                    } else if (type.equals("clothing")) {
                        System.out.print("Size: "); String size = scanner.nextLine();
                        System.out.print("Material: "); String material = scanner.nextLine();
                        inventory.addProduct(new ClothingProduct(id, name, price, qty, size, material));
                    } else if (type.equals("food")) {
                        System.out.print("Manufacture Date: "); String mfg = scanner.nextLine();
                        System.out.print("Expiry Date: "); String exp = scanner.nextLine();
                        inventory.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
                    }
                    break;
                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    inventory.removeProduct(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter Product ID: "); id = scanner.nextLine();
                    System.out.print("New Price: "); price = scanner.nextDouble();
                    System.out.print("New Quantity: "); qty = scanner.nextInt(); scanner.nextLine();
                    inventory.updateProduct(id, price, qty);
                    break;
                case 4:
                    System.out.print("Search by name or price? (n/p): ");
                    String opt = scanner.nextLine();
                    if (opt.equals("n")) {
                        System.out.print("Enter name: ");
                        List<Product> result = inventory.searchByName(scanner.nextLine());
                        result.forEach(Product::display);
                    } else {
                        System.out.print("Min price: "); double min = scanner.nextDouble();
                        System.out.print("Max price: "); double max = scanner.nextDouble(); scanner.nextLine();
                        List<Product> result = inventory.searchByPriceRange(min, max);
                        result.forEach(Product::display);
                    }
                    break;
                case 5:
                    inventory.displayAll();
                    break;
                case 6:
                    Order order = new Order();
                    while (true) {
                        System.out.print("Enter Product ID to add (or 'done'): ");
                        String pid = scanner.nextLine();
                        if (pid.equals("done")) break;
                        Product prod = inventory.getProductById(pid);
                        if (prod == null) {
                            System.out.println("Product not found.");
                            continue;
                        }
                        System.out.print("Quantity: ");
                        int q = scanner.nextInt(); scanner.nextLine();
                        try {
                            order.addItem(prod, q);
                            System.out.println("Added to order.");
                        } catch (OutOfStockException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    order.displayOrder();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
