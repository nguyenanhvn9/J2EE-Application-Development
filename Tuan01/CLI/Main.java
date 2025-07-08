/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CLI;
import java.util.Scanner;
import manager.*;
import model.*;
import Order.*;

/**
 *
 * @author DatG
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        InventoryManager inv = InventoryManager.getInstance();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Hiển thị tất cả sản phẩm");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Sửa thông tin sản phẩm");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    addProductCLI(inv, sc);
                    break;
                case 2:
                    inv.displayAll();
                    break;
                case 3:
                    createOrderCLI(inv, sc);
                    break;
                case 4:
                    deleteProductCLI(inv, sc);
                    break;
                case 5:
                    editProductCLI(inv, sc);
                    break;
                case 0:
                    System.out.println("👋 Thoát chương trình.");
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }

    private static void addProductCLI(InventoryManager inv, Scanner sc) {
        System.out.println("==> Nhập loại sản phẩm:");
        System.out.println("1. Điện tử\n2. Quần áo\n3. Thực phẩm");
        int type = sc.nextInt(); sc.nextLine();

        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("Giá: ");
        double price = sc.nextDouble();
        System.out.print("Số lượng tồn kho: ");
        int quantity = sc.nextInt(); sc.nextLine();

        Product p = null;
        switch (type) {
            case 1:
                System.out.print("Thời gian bảo hành (tháng): ");
                int warranty = sc.nextInt();
                System.out.print("Công suất (W): ");
                int power = sc.nextInt(); sc.nextLine();
                p = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                System.out.print("Size: ");
                String size = sc.nextLine();
                System.out.print("Chất liệu: ");
                String material = sc.nextLine();
                p = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                System.out.print("Ngày sản xuất: ");
                String mfg = sc.nextLine();
                System.out.print("Ngày hết hạn: ");
                String exp = sc.nextLine();
                p = new FoodProduct(id, name, price, quantity, mfg, exp);
                break;
            default:
                System.out.println("❌ Loại sản phẩm không hợp lệ.");
                return;
        }

        inv.addProduct(p);
        System.out.println("✅ Thêm sản phẩm thành công.");
    }

    private static void createOrderCLI(InventoryManager inv, Scanner sc) {
        Order order = new Order();
        while (true) {
            System.out.print("Nhập ID sản phẩm muốn mua (hoặc 0 để kết thúc): ");
            String id = sc.nextLine();
            if (id.equals("0")) break;

            Product product = inv.findById(id);
            if (product == null) {
                System.out.println("❌ Không tìm thấy sản phẩm.");
                continue;
            }

            System.out.print("Số lượng muốn mua: ");
            int qty = sc.nextInt(); sc.nextLine();

            try {
                order.addItem(product, qty);
                System.out.println("✔ Đã thêm vào đơn hàng.");
            } catch (OutOfStockException e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }

        System.out.println("🧾 Đơn hàng của bạn:");
        order.showOrder();
    }

    private static void deleteProductCLI(InventoryManager inv, Scanner sc) {
        System.out.print("Nhập ID sản phẩm muốn xóa: ");
        String id = sc.nextLine();
        Product p = inv.findById(id);
        if (p == null) {
            System.out.println("❌ Không tìm thấy sản phẩm.");
            return;
        }
        inv.removeProduct(id);
        System.out.println("✅ Đã xóa sản phẩm.");
    }

    private static void editProductCLI(InventoryManager inv, Scanner sc) {
        System.out.print("Nhập ID sản phẩm muốn sửa: ");
        String id = sc.nextLine();
        Product p = inv.findById(id);
        if (p == null) {
            System.out.println("❌ Không tìm thấy sản phẩm.");
            return;
        }
        System.out.print("Tên mới (" + p.getName() + "): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) p.setName(name);

        System.out.print("Giá mới (" + p.getPrice() + "): ");
        String priceStr = sc.nextLine();
        if (!priceStr.isEmpty()) p.setPrice(Double.parseDouble(priceStr));

        System.out.print("Số lượng mới (" + p.getQuantityInStock() + "): ");
        String qtyStr = sc.nextLine();
        if (!qtyStr.isEmpty()) p.setQuantityInStock(Integer.parseInt(qtyStr));

        // Nếu muốn sửa thêm thuộc tính riêng từng loại, có thể bổ sung tại đây

        System.out.println("✅ Đã cập nhật sản phẩm.");
    }
}
