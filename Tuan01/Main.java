import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== QUAN LY KHO HANG CUA HANG BAN LE =====");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Xoa san pham theo ID");
            System.out.println("3. Cap nhat thong tin san pham");
            System.out.println("4. Tim kiem san pham theo ten");
            System.out.println("5. Tim kiem san pham theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("0. Thoat");
            System.out.print("Ch: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProductMenu(); break;
                case 2: removeProductMenu(); break;
                case 3: updateProductMenu(); break;
                case 4: searchByNameMenu(); break;
                case 5: searchByPriceMenu(); break;
                case 6: inventory.displayAllProducts(); break;
                case 0: System.out.println("Tam biet!!!"); return;
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }
    private static void addProductMenu() {
        System.out.println("Chon loai san pham:");
        System.out.println("1. Dien tu");
        System.out.println("2. Quan ao");
        System.out.println("3. Thuc pham");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Ten: "); String name = scanner.nextLine();
        System.out.print("Gia: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong: "); int quantity = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                System.out.print("Bao hanh (thang): "); int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Cong suat (W): "); int power = Integer.parseInt(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                break;
            case 2:
                System.out.print("Size: "); String size = scanner.nextLine();
                System.out.print("Chat lieu: "); String material = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                break;
            case 3:
                System.out.print("Ngay san xuat (yyyy-mm-dd): "); LocalDate mfg = LocalDate.parse(scanner.nextLine());
                System.out.print("Han su dung (yyyy-mm-dd): "); LocalDate exp = LocalDate.parse(scanner.nextLine());
                inventory.addProduct(new FoodProduct(id, name, price, quantity, mfg, exp));
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
        }
    }

    private static void removeProductMenu() {
        System.out.print("Nhap ID san pham can xoa: ");
        String id = scanner.nextLine();
        if (inventory.removeProductById(id)) {
            System.out.println("Da xoa san pham.");
        } else {
            System.out.println("Khong tim thay san pham voi ID nay.");
        }
    }

    private static void updateProductMenu() {
        System.out.print("Nhap ID san pham can cap nhat: ");
        String id = scanner.nextLine();
        System.out.print("Gia moi: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong moi: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        if (inventory.updateProduct(id, price, quantity)) {
            System.out.println("Da cap nhat san pham.");
        } else {
            System.out.println("Khong tim thay san pham voi ID nay.");
        }
    }

    private static void searchByNameMenu() {
        System.out.print("Nhap ten san pham can tim: ");
        String name = scanner.nextLine();
        var results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham phu hop.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void searchByPriceMenu() {
        System.out.print("Nhap gia thap nhat: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhap gia cao nhat: ");
        double max = Double.parseDouble(scanner.nextLine());
        var results = inventory.searchByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham phu hop.");
        } else {
            results.forEach(Product::display);
        }
    }
} 