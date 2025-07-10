import exceptions.OutOfStockException;
import java.util.*;
import managers.InventoryManager;
import models.*;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        int choice;

        do {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị sản phẩm");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Loại (1: Điện tử, 2: Quần áo, 3: Thực phẩm): ");
                    int type = scanner.nextInt(); scanner.nextLine();
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("Tên: "); String name = scanner.nextLine();
                    System.out.print("Giá: "); double price = scanner.nextDouble();
                    System.out.print("Số lượng: "); int qty = scanner.nextInt(); scanner.nextLine();

                    if (type == 1) {
                        System.out.print("Bảo hành (tháng): "); int warranty = scanner.nextInt();
                        System.out.print("Công suất (W): "); int power = scanner.nextInt();
                        manager.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
                    } else if (type == 2) {
                        System.out.print("Size: "); String size = scanner.nextLine();
                        System.out.print("Chất liệu: "); String material = scanner.nextLine();
                        manager.addProduct(new ClothingProduct(id, name, price, qty, size, material));
                    } else if (type == 3) {
                        System.out.print("Ngày sản xuất: "); String mfg = scanner.nextLine();
                        System.out.print("Hạn sử dụng: "); String exp = scanner.nextLine();
                        manager.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
                    }
                    break;
                case 2:
                    manager.displayAllProducts();
                    break;
                case 3:
                    Order order = new Order();
                    System.out.print("Nhập ID sản phẩm: ");
                    String orderId = scanner.nextLine();
                    System.out.print("Số lượng: ");
                    int orderQty = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        order.addToOrder(orderId, orderQty);
                        order.displayOrder();
                    } catch (OutOfStockException e) {
                        System.out.println("❗ " + e.getMessage());
                    }
                    break;
            }

        } while (choice != 0);
        scanner.close();
    }
}
