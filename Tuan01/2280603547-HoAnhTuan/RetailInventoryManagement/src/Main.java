import manager.InventoryManager;
import models.*;
import order.*;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryManager inventory = InventoryManager.getInstance();
        boolean running = true;

        while (running) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị tất cả sản phẩm");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addProductCLI(inventory);
                case 2 -> inventory.displayAll();
                case 3 -> createOrderCLI();
                case 0 -> running = false;
            }
        }
    }

    static void addProductCLI(InventoryManager inv) {
        System.out.println("Loại sản phẩm: 1. Electronics | 2. Clothing | 3. Food");
        int type = Integer.parseInt(sc.nextLine());
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Tên: "); String name = sc.nextLine();
        System.out.print("Giá: "); double price = Double.parseDouble(sc.nextLine());
        System.out.print("Số lượng: "); int qty = Integer.parseInt(sc.nextLine());

        switch (type) {
            case 1 -> {
                System.out.print("Bảo hành (tháng): "); int warranty = Integer.parseInt(sc.nextLine());
                System.out.print("Công suất (W): "); int power = Integer.parseInt(sc.nextLine());
                inv.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
            }
            // case 2, 3: thêm tương tự ClothingProduct và FoodProduct
        }
    }

    static void createOrderCLI() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm (hoặc 'x' để kết thúc): ");
            String pid = sc.nextLine();
            if (pid.equals("x")) break;
            System.out.print("Số lượng: ");
            int qty = Integer.parseInt(sc.nextLine());

            try {
                order.addItem(pid, qty);
            } catch (OutOfStockException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
        order.displayOrder();
    }
}
