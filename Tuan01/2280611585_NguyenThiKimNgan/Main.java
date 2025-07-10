package Tuan01;

import Tuan01.InventoryManager;
import Tuan01.Order;
import Tuan01.ElectronicProduct;
import Tuan01.ClothingProduct;
import Tuan01.FoodProduct;
import Tuan01.OutOfStockException;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryManager inventory = InventoryManager.getInstance();

        while (true) {
            System.out.println("\n===== MENU CHINH =====");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Tao don hang");
            System.out.println("3. Thoat");
            System.out.print("Chon chuc nang: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> productMenu(inventory);
                case 2 -> orderMenu(inventory);
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void productMenu(InventoryManager inventory) {
        while (true) {
            System.out.println("\n----- QUAN LY SAN PHAM -----");
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Cap nhat san pham");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("7. Quay lai menu chinh");
            System.out.print("Nhap lua chon: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addProduct(inventory);
                case 2 -> {
                    System.out.print("Nhap ID can xoa: ");
                    String id = scanner.nextLine();
                    inventory.removeProduct(id);
                }
                case 3 -> {
                    System.out.print("Nhap ID san pham: ");
                    String id = scanner.nextLine();
                    System.out.print("Gia moi: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("So luong moi: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    inventory.updateProduct(id, price, qty);
                }
                case 4 -> {
                    System.out.print("Nhap tu khoa ten: ");
                    String keyword = scanner.nextLine();
                    inventory.findProductByName(keyword);
                }
                case 5 -> {
                    System.out.print("Gia tu: ");
                    double min = Double.parseDouble(scanner.nextLine());
                    System.out.print("Den: ");
                    double max = Double.parseDouble(scanner.nextLine());
                    inventory.findProductByPrice(min, max);
                }
                case 6 -> inventory.displayProducts();
                case 7 -> { return; }
                default -> System.out.println("Khong hop le.");
            }
        }
    }

    private static void addProduct(InventoryManager inventory) {
        System.out.println("\nThem san pham moi");
        System.out.print("Chon loai (1-Dien tu, 2-Quan ao, 3-Thuc pham): ");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ten: ");
        String name = scanner.nextLine();
        System.out.print("Gia: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong ton kho: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Cong suat (W): ");
                float power = Float.parseFloat(scanner.nextLine());
                System.out.print("Bao hanh (thang): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, quantity, power, warranty));
            }
            case 2 -> {
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Chat lieu: ");
                String material = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
            }
            case 3 -> {
                System.out.print("Ngay san xuat (yyyy-mm-dd): ");
                String mfg = scanner.nextLine();
                System.out.print("Han su dung (yyyy-mm-dd): ");
                String exp = scanner.nextLine();
                inventory.addProduct(new FoodProduct(id, name, price, quantity, mfg, exp));
            }
            default -> System.out.println("Loai san pham khong hop le.");
        }
    }

    private static void orderMenu(InventoryManager inventory) {
        Order order = new Order();
        System.out.println("\nTAO DON HANG:");

        while (true) {
            System.out.print("Nhap ID san pham (hoac 'done' de ket thuc): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;

            System.out.print("So luong: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            try {
                order.addItem(id, quantity);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }

        order.displayOrder();
    }
}