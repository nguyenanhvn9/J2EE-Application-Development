/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package retailinventorymanagement.main;

/**
 *
 * @author dtnhn
 */
import java.io.Serializable;
import java.util.*;
import java.time.LocalDate;
import retailinventorymanagement.exception.OutOfStockException;
import retailinventorymanagement.model.ClothingProduct;
import retailinventorymanagement.model.ElectronicProduct;
import retailinventorymanagement.model.FoodProduct;
import retailinventorymanagement.model.Product;
import retailinventorymanagement.service.InventoryManager;
import retailinventorymanagement.service.Order;

public class Main implements Serializable {

    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager manager = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n====== MENU ======");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Hiển thị tất cả");
            System.out.println("6. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 ->
                    addProduct();
                case 2 ->
                    removeProduct();
                case 3 ->
                    updateProduct();
                case 4 ->
                    searchProduct();
                case 5 ->
                    manager.displayAll();
                case 6 ->
                    createOrder();
                case 7 -> {
                    System.out.println("Tổng giá trị kho: " + manager.calculateTotalValue());
                    manager.getTopStocked(3).forEach(Product::display);
                }
                case 0 -> {
                    Scanner sc = new Scanner(System.in);
                    System.out.print("💾 Bạn có muốn lưu dữ liệu trước khi thoát không? (y/n): ");
                    String confirm = sc.nextLine().trim().toLowerCase();

                    if (confirm.equals("y")) {
                        manager.saveToCSV("inventory.csv");
                        System.out.println("✅ Dữ liệu đã được lưu.");
                    } else {
                        System.out.println("⚠️ Bạn đã chọn không lưu dữ liệu.");
                    }

                    System.out.println("👋 Tạm biệt!");
                    System.exit(0);
                }
                default ->
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Loại sản phẩm (1: Điện tử, 2: Quần áo, 3: Thực phẩm): ");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Tên: ");
        String name = scanner.nextLine();
        System.out.print("Giá: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng: ");
        int qty = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Bảo hành (tháng): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Công suất: ");
                int power = Integer.parseInt(scanner.nextLine());
                manager.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
            }
            case 2 -> {
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Chất liệu: ");
                String material = scanner.nextLine();
                manager.addProduct(new ClothingProduct(id, name, price, qty, size, material));
            }
            case 3 -> {
                System.out.print("Ngày sản xuất (yyyy-mm-dd): ");
                LocalDate mfg = LocalDate.parse(scanner.nextLine());
                System.out.print("Hạn sử dụng (yyyy-mm-dd): ");
                LocalDate exp = LocalDate.parse(scanner.nextLine());
                manager.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
            }
            default ->
                System.out.println("Loại không hợp lệ.");
        }
    }

    private static void removeProduct() {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = scanner.nextLine();
        manager.removeProduct(id);
    }

    private static void updateProduct() {
        System.out.print("Nhập ID: ");
        String id = scanner.nextLine();
        System.out.print("Giá mới: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng mới: ");
        int qty = Integer.parseInt(scanner.nextLine());
        manager.updateProduct(id, price, qty);
    }

    private static void searchProduct() {
        System.out.print("Tìm theo (1: Tên, 2: Khoảng giá): ");
        int opt = Integer.parseInt(scanner.nextLine());
        if (opt == 1) {
            System.out.print("Nhập tên: ");
            String name = scanner.nextLine();
            var list = manager.searchByName(name);
            list.forEach(Product::display);
        } else {
            System.out.print("Giá từ: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Đến: ");
            double max = Double.parseDouble(scanner.nextLine());
            var list = manager.searchByPriceRange(min, max);
            list.forEach(Product::display);
        }
    }

    private static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm (hoặc 'done' để hoàn tất): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) {
                break;
            }

            Product p = manager.getProduct(id);
            if (p == null) {
                System.out.println("Sản phẩm không tồn tại.");
                continue;
            }

            System.out.print("Số lượng: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addProduct(p, qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.displayOrder();
    }
}
