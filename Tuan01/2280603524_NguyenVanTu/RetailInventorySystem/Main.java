package Tuan01.RetailInventorySystem;

import Tuan01.RetailInventorySystem.manager.InventoryManager;
// import model.*;
import Tuan01.RetailInventorySystem.model.*;
import Tuan01.RetailInventorySystem.exception.OutOfStockException;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static InventoryManager im = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("""
                    \n---- MENU ----
                    1. Thêm sản phẩm
                    2. Xóa sản phẩm
                    3. Cập nhật sản phẩm
                    4. Tìm kiếm sản phẩm
                    5. Hiển thị tất cả sản phẩm
                    6. Tạo đơn hàng
                    0. Thoát
                    """);
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> {
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String id = sc.nextLine();
                    System.out.println(im.removeProductById(id) ? "Đã xóa." : "Không tìm thấy.");
                }
                case 3 -> {
                    System.out.print("ID cần cập nhật: ");
                    String id = sc.nextLine();
                    System.out.print("Giá mới: ");
                    double price = Double.parseDouble(sc.nextLine());
                    System.out.print("Số lượng mới: ");
                    int qty = Integer.parseInt(sc.nextLine());
                    System.out.println(im.updateProduct(id, price, qty) ? "Đã cập nhật." : "Không tìm thấy.");
                }
                case 4 -> {
                    System.out.print("Tìm theo tên: ");
                    String name = sc.nextLine();
                    im.searchByName(name).forEach(Product::display);
                }
                case 5 -> im.displayAll();
                case 6 -> processOrder();
                case 0 -> {
                    System.out.println("Tạm biệt!");
                    return;
                }
                default -> System.out.println("Chọn sai.");
            }
        }
    }

    static void addProduct() {
        System.out.println("""
                Loại sản phẩm:
                1. Electronics
                2. Clothing
                3. Food
                """);
        int type = Integer.parseInt(sc.nextLine());
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("Giá: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Số lượng: ");
        int qty = Integer.parseInt(sc.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Bảo hành (tháng): ");
                int warranty = Integer.parseInt(sc.nextLine());
                System.out.print("Công suất (W): ");
                int power = Integer.parseInt(sc.nextLine());
                im.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
            }
            case 2 -> {
                System.out.print("Size: ");
                String size = sc.nextLine();
                System.out.print("Chất liệu: ");
                String material = sc.nextLine();
                im.addProduct(new ClothingProduct(id, name, price, qty, size, material));
            }
            case 3 -> {
                System.out.print("Ngày SX (yyyy-mm-dd): ");
                LocalDate mfg = LocalDate.parse(sc.nextLine());
                System.out.print("Hạn dùng (yyyy-mm-dd): ");
                LocalDate exp = LocalDate.parse(sc.nextLine());
                im.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
            }
            default -> System.out.println("Loại không hợp lệ!");
        }
    }

    static void processOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm (hoặc 'done' để kết thúc): ");
            String id = sc.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            System.out.print("Số lượng: ");
            int qty = Integer.parseInt(sc.nextLine());
            try {
                order.addProduct(id, qty);
            } catch (OutOfStockException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
        order.printOrder();
    }
}
