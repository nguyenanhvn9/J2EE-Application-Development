import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static InventoryManager inventory = InventoryManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Them San Pham");
            System.out.println("2. Xoa San Pham");
            System.out.println("3. Cap Nhat San Pham");
            System.out.println("4. Tim Kiem Theo Ten");
            System.out.println("5. Tim Kiem Theo Khoang Gia");
            System.out.println("6. Hien Thi Tat Ca San Pham");
            System.out.println("7. Tao Don Hang");
            System.out.println("8. Thoat");
            System.out.print("Chon mot tuy chon: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Tieu thu ky tu newline

            switch (choice) {
                case 1: themSanPham(); break;
                case 2: xoaSanPham(); break;
                case 3: capNhatSanPham(); break;
                case 4: timKiemTheoTen(); break;
                case 5: timKiemTheoKhoangGia(); break;
                case 6: inventory.displayAllProducts(); break;
                case 7: taoDonHang(); break;
                case 8: System.out.println("Dang thoat..."); return;
                default: System.out.println("Tuy chon khong hop le!");
            }
        }
    }

    private static void themSanPham() {
        System.out.print("Nhap loai (1: Dien Tu, 2: Quan Ao, 3: Thuc Pham): ");
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nhap ID: ");
        String id = scanner.nextLine();
        System.out.print("Nhap Ten: ");
        String name = scanner.nextLine();
        System.out.print("Nhap Gia: ");
        double price = scanner.nextDouble();
        System.out.print("Nhap So Luong: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (type == 1) {
            System.out.print("Nhap Thoi Gian Bao Hanh (thang): ");
            int warranty = scanner.nextInt();
            System.out.print("Nhap Cong Suat (W): ");
            double power = scanner.nextDouble();
            inventory.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
        } else if (type == 2) {
            scanner.nextLine();
            System.out.print("Nhap Size: ");
            String size = scanner.nextLine();
            System.out.print("Nhap Chat Lieu: ");
            String material = scanner.nextLine();
            inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
        } else if (type == 3) {
            scanner.nextLine();
            System.out.print("Nhap Ngay San Xuat (yyyy-mm-dd): ");
            String manufactureDate = scanner.nextLine();
            System.out.print("Nhap Ngay Het Han (yyyy-mm-dd): ");
            String expiryDate = scanner.nextLine();
            inventory.addProduct(new FoodProduct(id, name, price, quantity, 
                    LocalDate.parse(manufactureDate), LocalDate.parse(expiryDate)));
        }
        System.out.println("San pham da duoc them thanh cong!");
    }

    private static void xoaSanPham() {
        System.out.print("Nhap ID san pham de xoa: ");
        String id = scanner.nextLine();
        inventory.removeProduct(id);
        System.out.println("San pham da duoc xoa thanh cong!");
    }

    private static void capNhatSanPham() {
        System.out.print("Nhap ID san pham de cap nhat: ");
        String id = scanner.nextLine();
        System.out.print("Nhap gia moi: ");
        double price = scanner.nextDouble();
        System.out.print("Nhap so luong moi: ");
        int quantity = scanner.nextInt();
        inventory.updateProduct(id, price, quantity);
        System.out.println("San pham da duoc cap nhat thanh cong!");
    }

    private static void timKiemTheoTen() {
        System.out.print("Nhap Ten de tim kiem: ");
        String name = scanner.nextLine();
        inventory.searchByName(name).forEach(Product::display);
    }

    private static void timKiemTheoKhoangGia() {
        System.out.print("Nhap Gia Toi Thieu: ");
        double min = scanner.nextDouble();
        System.out.print("Nhap Gia Toi Da: ");
        double max = scanner.nextDouble();
        inventory.searchByPriceRange(min, max).forEach(Product::display);
    }

    private static void taoDonHang() {
        Order order = new Order();
        System.out.print("Nhap ID san pham de dat hang: ");
        String id = scanner.nextLine();
        System.out.print("Nhap So Luong: ");
        int quantity = scanner.nextInt();
        try {
            order.addItem(id, quantity);
            System.out.println("Don hang da duoc tao thanh cong!");
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("Loi: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}