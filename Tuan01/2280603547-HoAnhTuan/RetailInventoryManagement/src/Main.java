import manager.InventoryManager; // Cần sử dụng InventoryManager
import models.*; // Cần sử dụng các lớp Product
import order.*; // Cần sử dụng Order và OutOfStockException

import java.time.LocalDate; // Để làm việc với ngày tháng
import java.time.format.DateTimeParseException; // Để bắt lỗi định dạng ngày
import java.util.List;
import java.util.Scanner;

public class Main {
    // Sử dụng Scanner tĩnh để đọc input từ console
    private static final Scanner sc = new Scanner(System.in);
    // Lấy instance duy nhất của InventoryManager
    private static final InventoryManager inventory = InventoryManager.getInstance();
    private static int orderCounter = 0; // Biến đếm để tạo orderId duy nhất

    public static void main(String[] args) {
        boolean running = true;

        // --- Dữ liệu mẫu để test nhanh ---
        inventory.addProduct(new ElectronicProduct("E001", "Laptop Gaming", 1500.0, 5, 24, 150));
        inventory.addProduct(new ClothingProduct("C001", "Denim Jeans", 35.0, 20, "L", "Denim"));
        inventory.addProduct(new FoodProduct("F001", "Organic Milk", 3.0, 15, LocalDate.of(2025, 6, 25), LocalDate.of(2025, 7, 10)));
        inventory.addProduct(new ElectronicProduct("E002", "Smartwatch X", 250.0, 12, 12, 5));
        // ----------------------------------

        while (running) {
            displayMainMenu(); // Hiển thị menu chính
            try {
                int choice = Integer.parseInt(sc.nextLine()); // Đọc lựa chọn của người dùng
                switch (choice) {
                    case 1 -> addProductCLI(); // Thêm sản phẩm
                    case 2 -> updateProductCLI(); // Cập nhật sản phẩm
                    case 3 -> removeProductCLI(); // Xóa sản phẩm
                    case 4 -> searchProductCLI(); // Tìm kiếm sản phẩm
                    case 5 -> inventory.displayAll(); // Hiển thị tất cả
                    case 6 -> createOrderCLI(); // Tạo đơn hàng
                    case 0 -> running = false; // Thoát ứng dụng
                    default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi nhập liệu: Vui lòng nhập một số.");
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi không mong muốn: " + e.getMessage());
            }
        }
        System.out.println("Đang thoát khỏi Hệ thống Quản lý Kho hàng. Tạm biệt!");
        sc.close(); // Đóng Scanner khi thoát ứng dụng
    }

    // Hiển thị menu chính của ứng dụng
    private static void displayMainMenu() {
        System.out.println("\n==== HỆ THỐNG QUẢN LÝ KHO HÀNG ====");
        System.out.println("1. Thêm sản phẩm mới");
        System.out.println("2. Cập nhật thông tin sản phẩm");
        System.out.println("3. Xóa sản phẩm");
        System.out.println("4. Tìm kiếm sản phẩm");
        System.out.println("5. Hiển thị tất cả sản phẩm");
        System.out.println("6. Tạo đơn hàng mới");
        System.out.println("0. Thoát");
        System.out.print("Nhập lựa chọn của bạn: ");
    }

    // Chức năng thêm sản phẩm qua CLI
    private static void addProductCLI() {
        System.out.println("\n--- Thêm Sản Phẩm Mới ---");
        System.out.print("Nhập ID sản phẩm: "); String id = sc.nextLine();
        if (inventory.getProductById(id) != null) {
            System.out.println("Lỗi: Sản phẩm với ID " + id + " đã tồn tại.");
            return;
        }

        System.out.print("Nhập Tên sản phẩm: "); String name = sc.nextLine();
        double price;
        int qty;

        try {
            System.out.print("Nhập Giá: "); price = Double.parseDouble(sc.nextLine());
            System.out.print("Nhập Số lượng trong kho: "); qty = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Giá hoặc Số lượng không hợp lệ. Vui lòng nhập một số.");
            return;
        }

        System.out.println("Chọn Loại sản phẩm:");
        System.out.println("  1. Điện tử");
        System.out.println("  2. Quần áo");
        System.out.println("  3. Thực phẩm");
        System.out.print("Nhập loại sản phẩm (1-3): ");
        try {
            int type = Integer.parseInt(sc.nextLine());
            Product newProduct = null;
            switch (type) {
                case 1 -> { // Điện tử
                    System.out.print("Nhập Thời gian bảo hành (tháng): "); int warranty = Integer.parseInt(sc.nextLine());
                    System.out.print("Nhập Công suất (W): "); int power = Integer.parseInt(sc.nextLine());
                    newProduct = new ElectronicProduct(id, name, price, qty, warranty, power);
                }
                case 2 -> { // Quần áo
                    System.out.print("Nhập Size (ví dụ: S, M, L, XL): "); String size = sc.nextLine();
                    System.out.print("Nhập Chất liệu (ví dụ: Cotton, Polyester): "); String material = sc.nextLine();
                    newProduct = new ClothingProduct(id, name, price, qty, size, material);
                }
                case 3 -> { // Thực phẩm
                    LocalDate mfgDate, expDate;
                    try {
                        System.out.print("Nhập Ngày sản xuất (YYYY-MM-DD): "); mfgDate = LocalDate.parse(sc.nextLine());
                        System.out.print("Nhập Ngày hết hạn (YYYY-MM-DD): "); expDate = LocalDate.parse(sc.nextLine());
                        if (expDate.isBefore(mfgDate)) {
                            System.out.println("Lỗi: Ngày hết hạn không thể trước ngày sản xuất.");
                            return;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Lỗi định dạng ngày: Vui lòng sử dụng định dạng YYYY-MM-DD.");
                        return;
                    }
                    newProduct = new FoodProduct(id, name, price, qty, mfgDate, expDate);
                }
                default -> {
                    System.out.println("Loại sản phẩm không hợp lệ.");
                    return;
                }
            }
            if (newProduct != null) {
                inventory.addProduct(newProduct);
                System.out.println("Sản phẩm đã được thêm thành công!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Dữ liệu thuộc tính riêng không hợp lệ (cần nhập số).");
        }
    }

    // Chức năng cập nhật sản phẩm qua CLI
    private static void updateProductCLI() {
        System.out.println("\n--- Cập Nhật Thông Tin Sản Phẩm ---");
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        String id = sc.nextLine();
        Product productToUpdate = inventory.getProductById(id);
        if (productToUpdate == null) {
            System.out.println("Lỗi: Không tìm thấy sản phẩm với ID: " + id);
            return;
        }

        System.out.println("Thông tin hiện tại:");
        productToUpdate.display();

        try {
            System.out.print("Nhập Giá mới (hoặc Enter để giữ nguyên: " + productToUpdate.getPrice() + "): ");
            String priceInput = sc.nextLine();
            double newPrice = priceInput.isEmpty() ? productToUpdate.getPrice() : Double.parseDouble(priceInput);

            System.out.print("Nhập Số lượng mới trong kho (hoặc Enter để giữ nguyên: " + productToUpdate.getQuantityInStock() + "): ");
            String qtyInput = sc.nextLine();
            int newQuantity = qtyInput.isEmpty() ? productToUpdate.getQuantityInStock() : Integer.parseInt(qtyInput);

            if (inventory.updateProduct(id, newPrice, newQuantity)) {
                System.out.println("Sản phẩm đã được cập nhật thành công!");
            } else {
                System.out.println("Lỗi: Không thể cập nhật sản phẩm."); // Nên không xảy ra nếu sản phẩm tồn tại
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Giá hoặc Số lượng không hợp lệ. Vui lòng nhập một số.");
        }
    }

    // Chức năng xóa sản phẩm qua CLI
    private static void removeProductCLI() {
        System.out.println("\n--- Xóa Sản Phẩm ---");
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = sc.nextLine();
        if (inventory.removeProduct(id)) {
            System.out.println("Sản phẩm với ID " + id + " đã được xóa thành công.");
        } else {
            System.out.println("Lỗi: Không tìm thấy sản phẩm với ID " + id + " để xóa.");
        }
    }

    // Chức năng tìm kiếm sản phẩm qua CLI
    private static void searchProductCLI() {
        System.out.println("\n--- Tìm Kiếm Sản Phẩm ---");
        System.out.println("1. Tìm kiếm theo Tên");
        System.out.println("2. Tìm kiếm theo Khoảng Giá");
        System.out.print("Nhập lựa chọn tìm kiếm của bạn: ");
        try {
            int searchChoice = Integer.parseInt(sc.nextLine());
            List<Product> results;
            switch (searchChoice) {
                case 1 -> {
                    System.out.print("Nhập tên sản phẩm (hoặc một phần tên): ");
                    String name = sc.nextLine();
                    results = inventory.searchByName(name);
                }
                case 2 -> {
                    System.out.print("Nhập giá tối thiểu: "); double minPrice = Double.parseDouble(sc.nextLine());
                    System.out.print("Nhập giá tối đa: "); double maxPrice = Double.parseDouble(sc.nextLine());
                    if (minPrice > maxPrice) {
                        System.out.println("Lỗi: Giá tối thiểu không thể lớn hơn giá tối đa.");
                        return;
                    }
                    results = inventory.searchByPriceRange(minPrice, maxPrice);
                }
                default -> {
                    System.out.println("Lựa chọn tìm kiếm không hợp lệ.");
                    return;
                }
            }

            if (results.isEmpty()) {
                System.out.println("Không tìm thấy sản phẩm nào phù hợp với tiêu chí của bạn.");
            } else {
                System.out.println("\n--- Kết Quả Tìm Kiếm ---");
                for (Product p : results) {
                    p.display();
                }
                System.out.println("------------------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi nhập liệu: Vui lòng nhập một số hợp lệ cho giá.");
        }
    }

    // Chức năng tạo đơn hàng qua CLI
    private static void createOrderCLI() {
        orderCounter++;
        String orderId = "ORD-" + String.format("%04d", orderCounter); // Tạo ID đơn hàng tự động
        Order order = new Order(orderId);
        System.out.printf("%n--- Đang tạo Đơn hàng mới (ID: %s) ---%n", orderId);

        while (true) {
            System.out.print("Nhập ID sản phẩm cần thêm (hoặc 'x' để hoàn tất đơn hàng): ");
            String pid = sc.nextLine();
            if (pid.equalsIgnoreCase("x")) { // Người dùng nhập 'x' để thoát
                break;
            }

            System.out.print("Nhập Số lượng cho sản phẩm " + pid + ": ");
            int qty;
            try {
                qty = Integer.parseInt(sc.nextLine());
                if (qty <= 0) {
                    System.out.println("Số lượng phải là một số dương.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi nhập liệu: Số lượng không hợp lệ. Vui lòng nhập một số.");
                continue;
            }

            try {
                order.addItem(pid, qty); // Cố gắng thêm sản phẩm vào đơn hàng
            } catch (OutOfStockException e) {
                System.out.println("Lỗi khi thêm vào đơn hàng: " + e.getMessage());
            }
        }
        order.displayOrder(); // Hiển thị tóm tắt đơn hàng sau khi hoàn tất
    }
}