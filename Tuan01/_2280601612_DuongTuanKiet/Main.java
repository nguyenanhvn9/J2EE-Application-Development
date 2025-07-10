package _2280601612_DuongTuanKiet;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static InventoryManager manager = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Thêm sản phẩm\n2. Xóa sản phẩm\n3. Cập nhật sản phẩm\n4. Tìm kiếm\n5. Hiển thị kho\n6. Tạo đơn hàng\n0. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1 -> themSanPham();
                case 2 -> {
                    System.out.print("Nhập ID: ");
                    manager.removeProduct(sc.nextLine());
                }
                case 3 -> {
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Giá mới: "); double price = sc.nextDouble();
                    System.out.print("Số lượng mới: "); int qty = sc.nextInt();
                    manager.updateProduct(id, price, qty);
                }
                case 4 -> timKiem();
                case 5 -> manager.displayAll();
                case 6 -> taoDonHang();
                case 0 -> System.exit(0);
            }
        }
    }

    private static void themSanPham() {
        System.out.print("Loại (1. Điện tử, 2. Quần áo, 3. Thực phẩm): ");
        int loai = sc.nextInt(); sc.nextLine();
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Tên: "); String name = sc.nextLine();
        System.out.print("Giá: "); double price = sc.nextDouble();
        System.out.print("Số lượng: "); int qty = sc.nextInt();

        switch (loai) {
            case 1 -> {
                System.out.print("Bảo hành (tháng): "); int bh = sc.nextInt();
                System.out.print("Công suất: "); int watt = sc.nextInt();
                manager.addProduct(new ElectronicProduct(id, name, price, qty, bh, watt));
            }
            case 2 -> {
                sc.nextLine();
                System.out.print("Size: "); String size = sc.nextLine();
                System.out.print("Chất liệu: "); String chatlieu = sc.nextLine();
                manager.addProduct(new ClothingProduct(id, name, price, qty, size, chatlieu));
            }
            case 3 -> {
                sc.nextLine();
                System.out.print("Ngày SX (yyyy-mm-dd): "); LocalDate mfg = LocalDate.parse(sc.nextLine());
                System.out.print("Hạn SD (yyyy-mm-dd): "); LocalDate exp = LocalDate.parse(sc.nextLine());
                manager.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
            }
        }
    }

    private static void timKiem() {
        System.out.print("Tìm theo (1. Tên, 2. Khoảng giá): ");
        int chon = sc.nextInt(); sc.nextLine();
        List<Product> result = new ArrayList<>();
        if (chon == 1) {
            System.out.print("Nhập tên: ");
            result = manager.searchByName(sc.nextLine());
        } else {
            System.out.print("Min: "); double min = sc.nextDouble();
            System.out.print("Max: "); double max = sc.nextDouble();
            result = manager.searchByPriceRange(min, max);
        }
        for (Product p : result) p.display();
    }

    private static void taoDonHang() {
        Order order = new Order();
        while (true) {
            System.out.print("ID SP (hoặc 'done'): "); String id = sc.nextLine();
            if (id.equals("done")) break;
            Product p = manager.getProductById(id);
            if (p == null) {
                System.out.println("Không tìm thấy sản phẩm");
                continue;
            }
            System.out.print("Số lượng: "); int qty = sc.nextInt(); sc.nextLine();
            try {
                order.addItem(p, qty);
                System.out.println("Đã thêm vào đơn hàng");
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.displayOrder();
    }
}
