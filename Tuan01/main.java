package Tuan01;

import Tuan01.manager.*;
import Tuan01.model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class main {
    private static final Scanner sc = new Scanner(System.in);
    private static final InventoryManager manager = InventoryManager.getInstance();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== RETAIL INVENTORY MENU =====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm theo tên");
            System.out.println("5. Tìm kiếm theo giá");
            System.out.println("6. Hiển thị tất cả sản phẩm");
            System.out.println("7. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> updateProduct();
                case 4 -> searchByName();
                case 5 -> searchByPrice();
                case 6 -> manager.displayAllProducts();
                case 7 -> createOrder();
            }
        } while (choice != 0);
    }

    private static void addProduct() {
        System.out.print("Loại sản phẩm (1. Điện tử, 2. Quần áo, 3. Thực phẩm): ");
        int type = Integer.parseInt(sc.nextLine());
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Tên: "); String name = sc.nextLine();
        System.out.print("Giá: "); double price = Double.parseDouble(sc.nextLine());
        System.out.print("Số lượng: "); int qty = Integer.parseInt(sc.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Bảo hành (tháng): "); int warranty = Integer.parseInt(sc.nextLine());
                System.out.print("Công suất (W): "); int power = Integer.parseInt(sc.nextLine());
                manager.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
            }
            case 2 -> {
                System.out.print("Size: "); String size = sc.nextLine();
                System.out.print("Chất liệu: "); String material = sc.nextLine();
                manager.addProduct(new ClothingProduct(id, name, price, qty, size, material));
            }
            case 3 -> {
                System.out.print("Ngày sản xuất (yyyy-mm-dd): ");
                LocalDate mfg = LocalDate.parse(sc.nextLine());
                System.out.print("Hạn sử dụng (yyyy-mm-dd): ");
                LocalDate exp = LocalDate.parse(sc.nextLine());
                manager.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
            }
        }
        System.out.println("✅ Sản phẩm đã được thêm.");
    }

    private static void removeProduct() {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = sc.nextLine();
        if (manager.removeProduct(id))
            System.out.println("✅ Đã xóa sản phẩm.");
        else
            System.out.println("❌ Không tìm thấy sản phẩm.");
    }

    private static void updateProduct() {
        System.out.print("Nhập ID sản phẩm: ");
        String id = sc.nextLine();
        System.out.print("Giá mới: "); double price = Double.parseDouble(sc.nextLine());
        System.out.print("Số lượng mới: "); int qty = Integer.parseInt(sc.nextLine());
        if (manager.updateProduct(id, price, qty))
            System.out.println("✅ Cập nhật thành công.");
        else
            System.out.println("❌ Không tìm thấy sản phẩm.");
    }

    private static void searchByName() {
        System.out.print("Nhập tên sản phẩm: ");
        String name = sc.nextLine();
        Product p = manager.searchByName(name);
        if (p != null) p.display();
        else System.out.println("❌ Không tìm thấy sản phẩm.");
    }

    private static void searchByPrice() {
        System.out.print("Nhập giá min: ");
        double min = Double.parseDouble(sc.nextLine());
        System.out.print("Nhập giá max: ");
        double max = Double.parseDouble(sc.nextLine());
        var list = manager.searchByPriceRange(min, max);
        if (list.isEmpty()) System.out.println("❌ Không có sản phẩm trong khoảng giá.");
        else list.forEach(Product::display);
    }

    private static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm (hoặc 'done' để kết thúc): ");
            String id = sc.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = manager.getProductById(id);
            if (p == null) {
                System.out.println("❌ Không tìm thấy sản phẩm.");
                continue;
            }
            System.out.print("Nhập số lượng: ");
            int qty = Integer.parseInt(sc.nextLine());
            try {
                order.addItem(p, qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.displayOrder();
    }
}
