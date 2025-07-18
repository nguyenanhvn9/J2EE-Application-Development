import entity.ClothingProduct;
import entity.ElectronicProduct;
import entity.FoodProduct;
import services.InventoryManager;
import services.Order;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        InventoryManager inventory = InventoryManager.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị tất cả sản phẩm");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
//            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Loại sản phẩm (1-Điện tử, 2-Quần áo, 3-Thực phẩm): ");
                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Tên: ");
                    String name = sc.nextLine();
                    System.out.print("Giá: ");
                    double price = sc.nextDouble();
                    System.out.print("Số lượng: ");
                    int quantity;
                    while (true){
                        try {
                            String input = sc.nextLine();
                            if (input.isEmpty()){
                                System.out.print("Số lượng: ");
                                continue;
                            }
                            quantity = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    switch (type) {
                        case 1:
                            System.out.print("Bảo hành (tháng): ");
                            int warranty = sc.nextInt();
                            System.out.print("Công suất (W): ");
                            int power = sc.nextInt();
                            inventory.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                            break;
                        case 2:
                            sc.nextLine();
                            System.out.print("Size: ");
                            String size = sc.nextLine();
                            System.out.print("Chất liệu: ");
                            String material = sc.nextLine();
                            inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                            break;
                        case 3:
                            sc.nextLine();
                            System.out.print("Ngày sản xuất (yyyy-mm-dd): ");
                            String mfgStr = sc.nextLine();
                            System.out.print("Ngày hết hạn (yyyy-mm-dd): ");
                            String expStr = sc.nextLine();
                            inventory.addProduct(new FoodProduct(id, name, price, quantity,
                                    LocalDate.parse(mfgStr), LocalDate.parse(expStr)));
                            break;
                    }
                    break;

                case 2:
                    inventory.displayAll();
                    break;

                case 3:
                    Order order = new Order();
                    while (true) {
                        System.out.print("Nhập ID sản phẩm (hoặc 'exit' để huỷ): ");
                        String pid = sc.nextLine();
                        if (pid.equalsIgnoreCase("exit")) break;
                        System.out.print("Số lượng: ");
                        String inputSl = sc.nextLine();
                        int qty = Integer.parseInt(inputSl);
                        try {
                            order.addProduct(pid, qty);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    order.printOrder();
                    break;

                case 4:
                    running = false;
                    break;
            }
        }

        sc.close();
    }
}
