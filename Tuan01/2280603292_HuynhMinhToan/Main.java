import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== QUẢN LÝ KHO HÀNG =====");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xóa sản phẩm theo ID");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Tìm kiếm sản phẩm theo khoảng giá");
            System.out.println("6. Hiển thị tất cả sản phẩm");
            System.out.println("7. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProductCLI(); break;
                case 2: removeProductCLI(); break;
                case 3: updateProductCLI(); break;
                case 4: searchByNameCLI(); break;
                case 5: searchByPriceCLI(); break;
                case 6: inventory.displayAllProducts(); break;
                case 7: createOrderCLI(); break;
                case 0: System.out.println("Tạm biệt!"); return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void addProductCLI() {
        System.out.println("Chọn loại sản phẩm: 1-Điện tử, 2-Quần áo, 3-Thực phẩm");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Tên: "); String name = scanner.nextLine();
        System.out.print("Giá: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng: "); int quantity = Integer.parseInt(scanner.nextLine());
        Product product = null;
        switch (type) {
            case 1:
                System.out.print("Bảo hành (tháng): "); int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Công suất (W): "); int power = Integer.parseInt(scanner.nextLine());
                product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                System.out.print("Size: "); String size = scanner.nextLine();
                System.out.print("Chất liệu: "); String material = scanner.nextLine();
                product = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                System.out.print("Ngày sản xuất (yyyy-mm-dd): "); LocalDate nsx = LocalDate.parse(scanner.nextLine());
                System.out.print("Hạn sử dụng (yyyy-mm-dd): "); LocalDate hsd = LocalDate.parse(scanner.nextLine());
                product = new FoodProduct(id, name, price, quantity, nsx, hsd);
                break;
            default:
                System.out.println("Loại sản phẩm không hợp lệ!");
                return;
        }
        inventory.addProduct(product);
        System.out.println("Đã thêm sản phẩm!");
    }

    private static void removeProductCLI() {
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        String id = scanner.nextLine();
        if (inventory.removeProductById(id)) {
            System.out.println("Đã xóa sản phẩm.");
        } else {
            System.out.println("Không tìm thấy sản phẩm.");
        }
    }

    private static void updateProductCLI() {
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        String id = scanner.nextLine();
        System.out.print("Giá mới: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Số lượng mới: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (inventory.updateProduct(id, price, quantity)) {
            System.out.println("Đã cập nhật sản phẩm.");
        } else {
            System.out.println("Không tìm thấy sản phẩm.");
        }
    }

    private static void searchByNameCLI() {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String name = scanner.nextLine();
        List<Product> results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void searchByPriceCLI() {
        System.out.print("Nhập giá thấp nhất: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập giá cao nhất: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Product> results = inventory.searchByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void createOrderCLI() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm muốn mua (hoặc 'x' để kết thúc): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("x")) break;
            Product product = inventory.getProductById(id);
            if (product == null) {
                System.out.println("Không tìm thấy sản phẩm.");
                continue;
            }
            System.out.print("Nhập số lượng muốn mua: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            try {
                order.addProduct(product, quantity);
                System.out.println("Đã thêm vào đơn hàng.");
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            order.processOrder();
            order.displayOrder();
            System.out.println("Đơn hàng đã được xử lý và cập nhật kho.");
        } catch (OutOfStockException e) {
            System.out.println("Lỗi khi xử lý đơn hàng: " + e.getMessage());
        }
    }
} 