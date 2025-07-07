package ui;

import inventory.InventoryManager;
import inventory.OutOfStockException;
import model.*;
import order.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị sản phẩm");
            System.out.println("3. Tìm kiếm sản phẩm");
            System.out.println("4. Cập nhật sản phẩm");
            System.out.println("5. Xóa sản phẩm");
            System.out.println("6. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> inventory.showAllProducts();
                case 3 -> searchProduct();
                case 4 -> updateProduct();
                case 5 -> deleteProduct();
                case 6 -> createOrder();
                case 0 -> System.exit(0);
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    static void addProduct() {
        System.out.println("Loại sản phẩm (1-Điện tử, 2-Quần áo, 3-Thực phẩm): ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Tên: "); String name = scanner.nextLine();
        System.out.print("Giá: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng: "); int quantity = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Bảo hành
