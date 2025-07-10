package Tuan01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n===== QUẢN LÝ KHO HÀNG =====");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xóa sản phẩm theo ID");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Tìm kiếm sản phẩm theo khoảng giá");
            System.out.println("6. Hiển thị tất cả sản phẩm trong kho");
            System.out.println("7. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer
            switch (choice) {
                case 1:
                    System.out.println("Chọn loại sản phẩm:");
                    System.out.println("1. Điện tử");
                    System.out.println("2. Quần áo");
                    System.out.println("3. Thực phẩm");
                    System.out.print("Nhập lựa chọn: ");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá: ");
                    double price = scanner.nextDouble();
                    System.out.print("Nhập số lượng tồn kho: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    Tuan01.models.Product product = null;
                    switch (type) {
                        case 1:
                            System.out.print("Nhập thời gian bảo hành (tháng): ");
                            int warranty = scanner.nextInt();
                            System.out.print("Nhập công suất (W): ");
                            double power = scanner.nextDouble();
                            scanner.nextLine();
                            product = new Tuan01.models.ElectronicProduct(id, name, price, quantity, warranty, power);
                            break;
                        case 2:
                            System.out.print("Nhập size: ");
                            String size = scanner.nextLine();
                            System.out.print("Nhập chất liệu: ");
                            String material = scanner.nextLine();
                            product = new Tuan01.models.ClothingProduct(id, name, price, quantity, size, material);
                            break;
                        case 3:
                            System.out.print("Nhập ngày sản xuất (dd/MM/yyyy): ");
                            String nsx = scanner.nextLine();
                            System.out.print("Nhập hạn sử dụng (dd/MM/yyyy): ");
                            String hsd = scanner.nextLine();
                            product = new Tuan01.models.FoodProduct(id, name, price, quantity, nsx, hsd);
                            break;
                        default:
                            System.out.println("Loại sản phẩm không hợp lệ!");
                    }
                    if (product != null) {
                        Tuan01.InventoryManager.getInstance().addProduct(product);
                        System.out.println("Đã thêm sản phẩm thành công!");
                    }
                    break;
                case 2:
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String removeId = scanner.nextLine();
                    boolean removed = Tuan01.InventoryManager.getInstance().removeProductById(removeId);
                    if (removed) {
                        System.out.println("Đã xóa sản phẩm thành công!");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm với ID này!");
                    }
                    break;
                case 3:
                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập giá mới: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Nhập số lượng mới: ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine();
                    boolean updated = Tuan01.InventoryManager.getInstance().updateProduct(updateId, newPrice, newQuantity);
                    if (updated) {
                        System.out.println("Đã cập nhật sản phẩm thành công!");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm với ID này!");
                    }
                    break;
                case 4:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String searchName = scanner.nextLine();
                    java.util.List<Tuan01.models.Product> foundByName = Tuan01.InventoryManager.getInstance().searchByName(searchName);
                    if (foundByName.isEmpty()) {
                        System.out.println("Không tìm thấy sản phẩm nào phù hợp!");
                    } else {
                        System.out.println("Kết quả tìm kiếm:");
                        for (Tuan01.models.Product p : foundByName) {
                            p.display();
                        }
                    }
                    break;
                case 5:
                    System.out.print("Nhập giá tối thiểu: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print("Nhập giá tối đa: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine();
                    java.util.List<Tuan01.models.Product> foundByPrice = Tuan01.InventoryManager.getInstance().searchByPriceRange(minPrice, maxPrice);
                    if (foundByPrice.isEmpty()) {
                        System.out.println("Không tìm thấy sản phẩm nào trong khoảng giá này!");
                    } else {
                        System.out.println("Kết quả tìm kiếm:");
                        for (Tuan01.models.Product p : foundByPrice) {
                            p.display();
                        }
                    }
                    break;
                case 6:
                    Tuan01.InventoryManager.getInstance().displayAllProducts();
                    break;
                case 7:
                    Tuan01.Order order = new Tuan01.Order();
                    while (true) {
                        System.out.print("Nhập ID sản phẩm muốn mua (hoặc 'x' để kết thúc): ");
                        String orderId = scanner.nextLine();
                        if (orderId.equalsIgnoreCase("x")) break;
                        Tuan01.models.Product prod = Tuan01.InventoryManager.getInstance().getProductById(orderId);
                        if (prod == null) {
                            System.out.println("Không tìm thấy sản phẩm với ID này!");
                            continue;
                        }
                        System.out.print("Nhập số lượng muốn mua: ");
                        int orderQty = scanner.nextInt();
                        scanner.nextLine();
                        order.addProduct(prod, orderQty);
                    }
                    try {
                        order.placeOrder();
                        System.out.println("Đặt hàng thành công!");
                        order.displayOrder();
                    } catch (Tuan01.OutOfStockException e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
        scanner.close();
    }
} 