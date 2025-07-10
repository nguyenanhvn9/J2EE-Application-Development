import java.util.*;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        try {
            inventory.loadFromFile("inventory.dat");
            System.out.println("Đã tải dữ liệu kho từ file.");
        } catch (Exception e) {
            System.out.println("Không tìm thấy file dữ liệu kho, bắt đầu với kho trống.");
        }
        while (true) {
            System.out.println("\n===== RETAIL INVENTORY MANAGEMENT =====");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xóa sản phẩm theo ID");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Tìm kiếm sản phẩm theo khoảng giá");
            System.out.println("6. Hiển thị tất cả sản phẩm");
            System.out.println("7. Tạo đơn hàng");
            System.out.println("8. Lưu kho hàng ra file");
            System.out.println("9. Đọc kho hàng từ file");
            System.out.println("10. Tính tổng giá trị kho hàng");
            System.out.println("11. Hiển thị sản phẩm sắp hết hàng (<5)");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProductMenu(); break;
                case 2: removeProductMenu(); break;
                case 3: updateProductMenu(); break;
                case 4: searchByNameMenu(); break;
                case 5: searchByPriceMenu(); break;
                case 6: inventory.displayAll(); break;
                case 7: createOrderMenu(); break;
                case 8: saveInventoryMenu(); break;
                case 9: loadInventoryMenu(); break;
                case 10: totalValueMenu(); break;
                case 11: lowStockMenu(); break;
                case 0: exitAndSave(); return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void addProductMenu() {
        System.out.println("Chọn loại sản phẩm:");
        System.out.println("1. Điện tử");
        System.out.println("2. Quần áo");
        System.out.println("3. Thực phẩm");
        System.out.print("Lựa chọn: ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Tên: "); String name = scanner.nextLine();
        System.out.print("Giá: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng: "); int quantity = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                System.out.print("Bảo hành (tháng): "); int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Công suất (W): "); int power = Integer.parseInt(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                break;
            case 2:
                System.out.print("Size: "); String size = scanner.nextLine();
                System.out.print("Chất liệu: "); String material = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                break;
            case 3:
                System.out.print("Ngày sản xuất (yyyy-mm-dd): "); LocalDate mfg = LocalDate.parse(scanner.nextLine());
                System.out.print("Hạn sử dụng (yyyy-mm-dd): "); LocalDate exp = LocalDate.parse(scanner.nextLine());
                inventory.addProduct(new FoodProduct(id, name, price, quantity, mfg, exp));
                break;
            default:
                System.out.println("Loại sản phẩm không hợp lệ!");
        }
        System.out.println("Đã thêm sản phẩm!");
    }

    private static void removeProductMenu() {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = scanner.nextLine();
        if (inventory.removeProductById(id)) {
            System.out.println("Đã xóa sản phẩm.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID này.");
        }
    }

    private static void updateProductMenu() {
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        String id = scanner.nextLine();
        System.out.print("Giá mới: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng mới: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (inventory.updateProduct(id, price, quantity)) {
            System.out.println("Đã cập nhật sản phẩm.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID này.");
        }
    }

    private static void searchByNameMenu() {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String name = scanner.nextLine();
        List<Product> results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm phù hợp.");
        } else {
            for (Product p : results) p.display();
        }
    }

    private static void searchByPriceMenu() {
        System.out.print("Nhập giá thấp nhất: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập giá cao nhất: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Product> results = inventory.searchByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm phù hợp.");
        } else {
            for (Product p : results) p.display();
        }
    }

    private static void createOrderMenu() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm muốn mua (hoặc 'done' để kết thúc): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(id);
            if (p == null) {
                System.out.println("Không tìm thấy sản phẩm với ID này.");
                continue;
            }
            System.out.print("Nhập số lượng: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addItem(p, qty);
                System.out.println("Đã thêm vào đơn hàng.");
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            order.processOrder();
            System.out.println("Đơn hàng đã được xử lý thành công!");
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("Đơn hàng thất bại: " + e.getMessage());
        }
    }

    private static void saveInventoryMenu() {
        try {
            inventory.saveToFile("inventory.dat");
            System.out.println("Đã lưu kho hàng ra file.");
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }

    private static void loadInventoryMenu() {
        try {
            inventory.loadFromFile("inventory.dat");
            System.out.println("Đã đọc kho hàng từ file.");
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    private static void totalValueMenu() {
        double total = inventory.getTotalInventoryValue();
        System.out.println("Tổng giá trị kho hàng: " + total);
    }

    private static void lowStockMenu() {
        List<Product> lowStock = inventory.getLowStockProducts(5);
        if (lowStock.isEmpty()) {
            System.out.println("Không có sản phẩm nào sắp hết hàng.");
        } else {
            System.out.println("Các sản phẩm sắp hết hàng:");
            for (Product p : lowStock) p.display();
        }
    }

    private static void exitAndSave() {
        try {
            inventory.saveToFile("inventory.dat");
            System.out.println("Đã lưu kho hàng trước khi thoát.");
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
        System.out.println("Tạm biệt!");
    }
} 