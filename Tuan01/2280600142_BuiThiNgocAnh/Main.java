import java.util.*;
import java.time.LocalDate;

// Chương trình chính với giao diện dòng lệnh
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = getIntInput("Chọn chức năng: ");
            switch (choice) {
                case 1:
                    productManagementMenu();
                    break;
                case 2:
                    createOrder();
                    break;
                case 0:
                    System.out.println("Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Hiển thị menu chính
    private static void showMenu() {
        System.out.println("\n===== QUẢN LÝ KHO HÀNG =====");
        System.out.println("1. Quản lý sản phẩm");
        System.out.println("2. Tạo đơn hàng");
        System.out.println("0. Thoát");
    }

    // Menu quản lý sản phẩm
    private static void productManagementMenu() {
        while (true) {
            System.out.println("\n--- Quản lý sản phẩm ---");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xóa sản phẩm theo ID");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Tìm kiếm sản phẩm theo khoảng giá");
            System.out.println("6. Hiển thị tất cả sản phẩm");
            System.out.println("0. Quay lại");
            int choice = getIntInput("Chọn chức năng: ");
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    searchByPriceRange();
                    break;
                case 6:
                    inventory.displayAll();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Thêm sản phẩm mới
    private static void addProduct() {
        System.out.println("Chọn loại sản phẩm:");
        System.out.println("1. Điện tử");
        System.out.println("2. Quần áo");
        System.out.println("3. Thực phẩm");
        int type = getIntInput("Loại: ");
        String id = getStringInput("Mã sản phẩm: ");
        String name = getStringInput("Tên sản phẩm: ");
        double price = getDoubleInput("Giá: ");
        int quantity = getIntInput("Số lượng tồn kho: ");
        Product product = null;
        switch (type) {
            case 1:
                int warranty = getIntInput("Thời gian bảo hành (tháng): ");
                double power = getDoubleInput("Công suất (W): ");
                product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                String size = getStringInput("Kích cỡ: ");
                String material = getStringInput("Chất liệu: ");
                product = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                String nsx = getStringInput("Ngày sản xuất (yyyy-MM-dd): ");
                String hsd = getStringInput("Hạn sử dụng (yyyy-MM-dd): ");
                try {
                    LocalDate manufactureDate = LocalDate.parse(nsx);
                    LocalDate expiryDate = LocalDate.parse(hsd);
                    product = new FoodProduct(id, name, price, quantity, manufactureDate, expiryDate);
                } catch (Exception e) {
                    System.out.println("Định dạng ngày không hợp lệ!");
                    return;
                }
                break;
            default:
                System.out.println("Loại sản phẩm không hợp lệ!");
                return;
        }
        inventory.addProduct(product);
        System.out.println("Đã thêm sản phẩm thành công!");
    }

    // Xóa sản phẩm
    private static void removeProduct() {
        String id = getStringInput("Nhập mã sản phẩm cần xóa: ");
        if (inventory.removeProduct(id)) {
            System.out.println("Đã xóa sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm!");
        }
    }

    // Cập nhật sản phẩm
    private static void updateProduct() {
        String id = getStringInput("Nhập mã sản phẩm cần cập nhật: ");
        double price = getDoubleInput("Giá mới: ");
        int quantity = getIntInput("Số lượng mới: ");
        if (inventory.updateProduct(id, price, quantity)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm!");
        }
    }

    // Tìm kiếm theo tên
    private static void searchByName() {
        String name = getStringInput("Nhập tên sản phẩm cần tìm: ");
        List<Product> result = inventory.searchByName(name);
        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm phù hợp!");
        } else {
            for (Product p : result) {
                p.display();
            }
        }
    }

    // Tìm kiếm theo khoảng giá
    private static void searchByPriceRange() {
        double min = getDoubleInput("Giá thấp nhất: ");
        double max = getDoubleInput("Giá cao nhất: ");
        List<Product> result = inventory.searchByPriceRange(min, max);
        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm phù hợp!");
        } else {
            for (Product p : result) {
                p.display();
            }
        }
    }

    // Tạo đơn hàng
    private static void createOrder() {
        Order order = new Order();
        while (true) {
            String id = getStringInput("Nhập mã sản phẩm muốn mua (hoặc 'x' để kết thúc): ");
            if (id.equalsIgnoreCase("x")) break;
            Product product = inventory.getProductById(id);
            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm!");
                continue;
            }
            int quantity = getIntInput("Nhập số lượng muốn mua: ");
            order.addItem(product, quantity);
        }
        try {
            order.processOrder();
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // Hàm nhập số nguyên an toàn
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }

    // Hàm nhập số thực an toàn
    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số thực!");
            }
        }
    }

    // Hàm nhập chuỗi
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
} 