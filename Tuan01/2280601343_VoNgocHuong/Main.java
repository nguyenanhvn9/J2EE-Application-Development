package InventorySystem;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager manager = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== He thong Quan ly Kho hang =====");
            System.out.println("1. Tao san pham moi");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Cap nhat san pham");
            System.out.println("4. Tim kiem san pham theo ten");
            System.out.println("5. Tim kiem san pham theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("7. Tao don hang");
            System.out.println("0. Thoat");
            System.out.print("Lua chon: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: addProductCLI(); break;
                case 2: removeProductCLI(); break;
                case 3: updateProductCLI(); break;
                case 4: searchByNameCLI(); break;
                case 5: searchByPriceCLI(); break;
                case 6: manager.displayAll(); break;
                case 7: createOrderCLI(); break;
                case 0: System.exit(0);
                default: System.out.println("Invalid!");
            }
        }
    }

    private static void addProductCLI() {
        System.out.println("Chon loai san pham: 1-Dien tu 2-Quan ao 3-Thuc pham");
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Ten: "); String name = scanner.nextLine();
        System.out.print("Gia: "); double price = scanner.nextDouble();
        System.out.print("So luong ton: "); int stock = scanner.nextInt();
        scanner.nextLine();
        Product p = null;
        if (type == 1) {
            System.out.print("Bao hanh (thang): "); int w = scanner.nextInt();
            System.out.print("Cong suat (W): "); int power = scanner.nextInt();
            p = new ElectronicProduct(id, name, price, stock, w, power);
        } else if (type == 2) {
            scanner.nextLine();
            System.out.print("Size: "); String size = scanner.nextLine();
            System.out.print("Chat lieu: "); String material = scanner.nextLine();
            p = new ClothingProduct(id, name, price, stock, size, material);
        } else if (type == 3) {
            scanner.nextLine();
            System.out.print("Ngay san xuat (yyyy-mm-dd): "); String mfg = scanner.nextLine();
            System.out.print("Ngay het han (yyyy-mm-dd): "); String exp = scanner.nextLine();
            p = new FoodProduct(id, name, price, stock, LocalDate.parse(mfg), LocalDate.parse(exp));
        }
        if (p != null) {
            manager.addProduct(p);
            System.out.println("Da them!");
        }
    }

    private static void removeProductCLI() {
        System.out.print("Enter ID to remove: ");
        String id = scanner.nextLine();
        manager.removeProduct(id);
        System.out.println("Removed!");
    }

    private static void updateProductCLI() {
        System.out.print("Enter ID to update: ");
        String id = scanner.nextLine();
        System.out.print("New price: ");
        double price = scanner.nextDouble();
        System.out.print("New stock: ");
        int stock = scanner.nextInt();
        manager.updateProduct(id, price, stock);
        System.out.println("Updated!");
    }

    private static void searchByNameCLI() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        List<Product> found = manager.searchByName(name);
        for (Product p : found) p.display();
    }

    private static void searchByPriceCLI() {
        System.out.print("Min price: ");
        double min = scanner.nextDouble();
        System.out.print("Max price: ");
        double max = scanner.nextDouble();
        List<Product> found = manager.searchByPriceRange(min, max);
        for (Product p : found) p.display();
    }

    private static void createOrderCLI() {
        Order order = new Order();
        while (true) {
            System.out.print("Enter product ID (or 'done'): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = manager.getProduct(id);
            if (p == null) {
                System.out.println("Not found!");
                continue;
            }
            System.out.print("Quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            try {
                order.addItem(p, qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.processOrder();
        order.displayOrder();
        System.out.println("Order processed!");
    }
}
