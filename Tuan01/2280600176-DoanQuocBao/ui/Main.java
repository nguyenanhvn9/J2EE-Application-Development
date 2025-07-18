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
                System.out.print("Bảo hành (tháng): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Công suất (W): ");
                int power = Integer.parseInt(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
            }
            case 2 -> {
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Chất liệu: ");
                String material = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
            }
            case 3 -> {
                System.out.print("Ngày sản xuất (yyyy-MM-dd): ");
                LocalDate nsx = LocalDate.parse(scanner.nextLine());
                System.out.print("Hạn sử dụng (yyyy-MM-dd): ");
                LocalDate hsd = LocalDate.parse(scanner.nextLine());
                inventory.addProduct(new FoodProduct(id, name, price, quantity, nsx, hsd));
            }
            default -> System.out.println("Loại sản phẩm không hợp lệ.");
        }
    }

    static void searchProduct() {
        System.out.println("1. Tìm theo tên");
        System.out.println("2. Tìm theo khoảng giá");
        System.out.print("Chọn: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập tên cần tìm: ");
                String keyword = scanner.nextLine();
                List<Product> foundByName = inventory.searchByName(keyword);
                foundByName.forEach(Product::display);
            }
            case 2 -> {
                System.out.print("Giá thấp nhất: ");
                double min = Double.parseDouble(scanner.nextLine());
                System.out.print("Giá cao nhất: ");
                double max = Double.parseDouble(scanner.nextLine());
                List<Product> foundByPrice = inventory.searchByPriceRange(min, max);
                foundByPrice.forEach(Product::display);
            }
            default -> System.out.println("Lựa chọn không hợp lệ.");
        }
    }

    static void updateProduct() {
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        String id = scanner.nextLine();
        System.out.print("Giá mới: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng mới: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        inventory.updateProduct(id, price, quantity);
        System.out.println("Đã cập nhật sản phẩm.");
    }

    static void deleteProduct() {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = scanner.nextLine();
        inventory.removeProduct(id);
        System.out.println("Đã xóa sản phẩm.");
    }

    static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm (hoặc 'x' để hoàn tất): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("x")) break;
            System.out.print("Số lượng: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addItem(id, qty);
            } catch (OutOfStockException e) {
                System.out.println("Lỗi: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
        System.out.println("\n--- Đơn hàng ---");
        order.showOrder();
    }
}

