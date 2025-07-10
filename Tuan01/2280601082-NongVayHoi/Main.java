import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== RETAIL INVENTORY MANAGEMENT =====");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Xoa san pham theo ID");
            System.out.println("3. Cap nhat thong tin san pham");
            System.out.println("4. Tim kiem san pham theo ten");
            System.out.println("5. Tim kiem san pham theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("7. Tao don hang");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProductMenu(); break;
                case 2: removeProductMenu(); break;
                case 3: updateProductMenu(); break;
                case 4: searchByNameMenu(); break;
                case 5: searchByPriceMenu(); break;
                case 6: inventory.displayAll(); break;
                case 7: createOrderMenu(); break;
                case 0: System.exit(0);
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void addProductMenu() {
        System.out.println("Chon loai san pham: 1-Dien tu, 2-Quan ao, 3-Thuc pham");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: "); String id = scanner.nextLine();
        System.out.print("Ten: "); String name = scanner.nextLine();
        System.out.print("Gia: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong: "); int qty = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1:
                System.out.print("Bao hanh (thang): "); int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Cong suat: "); String power = scanner.nextLine();
                inventory.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
                break;
            case 2:
                System.out.print("Size: "); String size = scanner.nextLine();
                System.out.print("Chat lieu: "); String material = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, qty, size, material));
                break;
            case 3:
                System.out.print("Ngay san xuat (yyyy-mm-dd): "); LocalDate mfg = LocalDate.parse(scanner.nextLine());
                System.out.print("Ngay het han (yyyy-mm-dd): "); LocalDate exp = LocalDate.parse(scanner.nextLine());
                inventory.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
        }
    }

    private static void removeProductMenu() {
        System.out.print("Nhap ID san pham can xoa: ");
        String id = scanner.nextLine();
        inventory.removeProduct(id);
        System.out.println("Da xoa (neu ton tai).");
    }

    private static void updateProductMenu() {
        System.out.print("Nhap ID san pham can cap nhat: ");
        String id = scanner.nextLine();
        System.out.print("Gia moi: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong moi: ");
        int qty = Integer.parseInt(scanner.nextLine());
        inventory.updateProduct(id, price, qty);
        System.out.println("Da cap nhat (neu ton tai).");
    }

    private static void searchByNameMenu() {
        System.out.print("Nhap ten san pham can tim: ");
        String name = scanner.nextLine();
        List<Product> results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void searchByPriceMenu() {
        System.out.print("Gia thap nhat: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Gia cao nhat: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Product> results = inventory.searchByPrice(min, max);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham.");
        } else {
            results.forEach(Product::display);
        }
    }

    private static void createOrderMenu() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhap ID san pham muon mua (hoac 'done' de ket thuc): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(id);
            if (p == null) {
                System.out.println("Khong tim thay san pham!");
                continue;
            }
            System.out.print("So luong: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addItem(p, qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.process();
        order.displayOrder();
        System.out.println("Don hang da duoc xu ly!");
    }
} 