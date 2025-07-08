package product;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Xóa bộ nhớ đệm

            switch (choice) {
                case 1:
                    quanLySanPham(manager, sc);
                    break;
                case 2:
                    taoDonHang(manager, sc);
                    break;
                case 0:
                    running = false;
                    System.out.println("👋 Tạm biệt!");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
        sc.close();
    }

    private static void quanLySanPham(InventoryManager manager, Scanner sc) {
        while (true) {
            System.out.println("\n=== QUẢN LÝ SẢN PHẨM ===");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Cập nhật giá");
            System.out.println("4. Cập nhật số lượng");
            System.out.println("5. Tìm theo tên");
            System.out.println("6. Tìm theo khoảng giá");
            System.out.println("7. Xem tất cả sản phẩm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    themSanPham(manager, sc);
                    break;
                case 2:
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String id = sc.nextLine();
                    manager.removeProduct(id);
                    break;
                case 3:
                    System.out.print("Nhập ID sản phẩm: ");
                    String updateId = sc.nextLine();
                    System.out.print("Nhập giá mới: ");
                    float newPrice = sc.nextFloat();
                    manager.updatePrice(updateId, newPrice);
                    break;
                case 4:
                    System.out.print("Nhập ID sản phẩm: ");
                    String qtyId = sc.nextLine();
                    System.out.print("Nhập số lượng mới: ");
                    int newQty = sc.nextInt();
                    manager.updateQuantity(qtyId, newQty);
                    break;
                case 5:
                    System.out.print("Nhập tên cần tìm: ");
                    String searchName = sc.nextLine();
                    List<Product> foundByName = manager.searchByName(searchName);
                    for (Product p : foundByName) {
                        p.display();
                    }
                    break;
                case 6:
                    System.out.print("Nhập giá tối thiểu: ");
                    float min = sc.nextFloat();
                    System.out.print("Nhập giá tối đa: ");
                    float max = sc.nextFloat();
                    List<Product> foundByPrice = manager.searchByPriceRange(min, max);
                    for (Product p : foundByPrice) {
                        p.display();
                    }
                    break;
                case 7:
                    manager.displayAllProducts();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themSanPham(InventoryManager manager, Scanner sc) {
        System.out.println("\nChọn loại sản phẩm:");
        System.out.println("1. Điện tử");
        System.out.println("2. Quần áo");
        System.out.println("3. Thực phẩm");
        System.out.print("Chọn: ");

        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("Giá: ");
        float price = sc.nextFloat();
        System.out.print("Số lượng: ");
        int qty = sc.nextInt();
        sc.nextLine();

        Product product = null;
        switch (type) {
            case 1:
                System.out.print("Thời gian bảo hành (tháng): ");
                int warranty = sc.nextInt();
                sc.nextLine();
                System.out.print("Công suất: ");
                String power = sc.nextLine();
                product = new ElectronicProduct(id, name, price, qty, warranty, power);
                break;
            case 2:
                System.out.print("Kích cỡ: ");
                String size = sc.nextLine();
                System.out.print("Chất liệu: ");
                String material = sc.nextLine();
                product = new ClothingProduct(id, name, price, qty, size, material);
                break;
            case 3:
                System.out.print("Ngày hết hạn (yyyy-MM-dd): ");
                String expDate = sc.nextLine();
                System.out.print("Điều kiện bảo quản: ");
                String storage = sc.nextLine();
                product = new FoodProduct(id, name, price, qty, expDate, storage);
                break;
        }

        if (product != null) {
            manager.addProduct(product);
        }
    }

    private static void taoDonHang(InventoryManager manager, Scanner sc) {
        Order order = new Order();

        while (true) {
            System.out.print("\nNhập ID sản phẩm (hoặc 'x' để kết thúc): ");
            String id = sc.nextLine();
            if (id.equalsIgnoreCase("x")) break;

            System.out.print("Nhập số lượng: ");
            int qty = sc.nextInt();
            sc.nextLine();

            try {
                // Giả sử InventoryManager có phương thức này
                manager.processOrder(id, qty, order);
            } catch (OutOfStockException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
        order.displayOrder();
    }
}