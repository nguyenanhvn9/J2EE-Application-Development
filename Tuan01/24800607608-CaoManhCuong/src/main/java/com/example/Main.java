package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Tạo đơn hàng");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    productMenu(scanner, manager);
                    break;
                case 2:
                    createOrder(scanner, manager);
                    break;
                case 3:
                    System.out.println("👋 Thoát chương trình.");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }

        } while (choice != 3);
    }

    private static void productMenu(Scanner scanner, InventoryManager manager) {
        System.out.println("\n-- QUẢN LÝ SẢN PHẨM --");
        System.out.println("1. Thêm sản phẩm mẫu");
        System.out.println("2. Hiển thị tất cả sản phẩm");
        System.out.println("3. Xoá theo ID");
        System.out.print("Chọn: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                manager.addProduct(new ElectronicProduct("E001", "Laptop", 1000, 5, 24));
                manager.addProduct(new ClothingProduct("C001", "Áo sơ mi", 200, 10, "M", "Linen"));
                manager.addProduct(new FoodProduct("F001", "Chuối", 20, 50, "2025-05-01"));
                break;
            case 2:
                manager.displayAllProducts();
                break;
            case 3:
                System.out.print("Nhập ID sản phẩm cần xoá: ");
                String id = scanner.nextLine();
                manager.removeProductById(id);
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    private static void createOrder(Scanner scanner, InventoryManager manager) {
        Order order = new Order();
        String more;

        do {
            System.out.print("Nhập ID sản phẩm: ");
            String id = scanner.nextLine();
            Product product = manager.getProductById(id);

            if (product == null) {
                System.out.println("❌ Không tìm thấy sản phẩm!");
            } else {
                System.out.print("Nhập số lượng: ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                try {
                    order.addItem(product, qty);
                    System.out.println("✔ Đã thêm vào đơn hàng.");
                } catch (OutOfStockException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.print("Thêm sản phẩm khác? (y/n): ");
            more = scanner.nextLine();

        } while (more.equalsIgnoreCase("y"));

        order.displayOrder();
    }
}

