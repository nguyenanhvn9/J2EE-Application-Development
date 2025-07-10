import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        InventoryManager inventory = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Hiển thị tất cả sản phẩm");
            System.out.println("6. Tạo đơn hàng");
            System.out.println("0. Thoát");

            System.out.print("Chọn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.println("Chọn loại sản phẩm (1: Electronic, 2: Clothing, 3: Food): ");
                    int type = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Giá: ");
                    double price = scanner.nextDouble();
                    System.out.print("Số lượng: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();

                    switch (type) {
                        case 1 -> {
                            System.out.print("Bảo hành (tháng): ");
                            int warranty = scanner.nextInt();
                            inventory.addProduct(new ElectronicProduct(id, name, price, qty, warranty));
                        }
                        case 2 -> {
                            System.out.print("Size: ");
                            String size = scanner.nextLine();
                            inventory.addProduct(new ClothingProduct(id, name, price, qty, size));
                        }
                        case 3 -> {
                            System.out.print("Hạn sử dụng: ");
                            String exp = scanner.nextLine();
                            inventory.addProduct(new FoodProduct(id, name, price, qty, exp));
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String id = scanner.nextLine();
                    inventory.removeProductById(id);
                }

                case 3 -> {
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Giá mới: ");
                    double price = scanner.nextDouble();
                    System.out.print("Số lượng mới: ");
                    int qty = scanner.nextInt();
                    inventory.updateProduct(id, price, qty);
                }

                case 4 -> {
                    System.out.println("1. Tìm theo tên | 2. Theo khoảng giá");
                    int opt = scanner.nextInt();
                    scanner.nextLine();

                    if (opt == 1) {
                        System.out.print("Nhập tên: ");
                        String keyword = scanner.nextLine();
                        var result = inventory.searchByName(keyword);
                        result.forEach(Product::display);
                    } else {
                        System.out.print("Min: ");
                        double min = scanner.nextDouble();
                        System.out.print("Max: ");
                        double max = scanner.nextDouble();
                        var result = inventory.searchByPriceRange(min, max);
                        result.forEach(Product::display);
                    }
                }

                case 5 -> inventory.displayAll();

                case 6 -> {
                    Order order = new Order();
                    while (true) {
                        System.out.print("Nhập ID sản phẩm cần mua (hoặc 'done'): ");
                        String id = scanner.nextLine();
                        if (id.equals("done"))
                            break;

                        System.out.print("Số lượng: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            order.addProduct(id, qty);
                        } catch (OutOfStockException e) {
                            System.out.println("Lỗi " + e.getMessage());
                        }
                    }
                    order.displayOrder();
                }

                case 0 -> running = false;

                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        scanner.close();
    }
}
