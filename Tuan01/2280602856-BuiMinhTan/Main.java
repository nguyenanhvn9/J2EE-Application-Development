import java.util.Scanner;

public class Main {
    private static InventoryManager inventory = InventoryManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== He Thong Quan Ly Kho Ban Le ====");
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham theo ID");
            System.out.println("3. Cap nhat san pham");
            System.out.println("4. Tim kiem san pham theo ten");
            System.out.println("5. Tim kiem san pham theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("7. Tao don hang");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: addProduct(); break;
                case 2: removeProduct(); break;
                case 3: updateProduct(); break;
                case 4: searchByName(); break;
                case 5: searchByPrice(); break;
                case 6: inventory.displayAllProducts(); break;
                case 7: createOrder(); break;
                case 0: return;
                default: System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Loai san pham (1=Dien tu, 2=Quan ao, 3=Thuc pham): ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("Ma san pham (ID): "); String id = scanner.nextLine();
        System.out.print("Ten san pham: "); String name = scanner.nextLine();
        System.out.print("Gia: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong: "); int qty = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                System.out.print("Thoi gian bao hanh (thang): ");
                int w = Integer.parseInt(scanner.nextLine());
                System.out.print("Cong suat (W): ");
                double power = Double.parseDouble(scanner.nextLine());
                inventory.addProduct(new ElectronicProduct(id, name, price, qty, w, power));
                break;
            case 2:
                System.out.print("Kich co: ");
                String size = scanner.nextLine();
                System.out.print("Chat lieu: ");
                String mat = scanner.nextLine();
                inventory.addProduct(new ClothingProduct(id, name, price, qty, size, mat));
                break;
            case 3:
                System.out.print("Ngay san xuat: ");
                String mfg = scanner.nextLine();
                System.out.print("Ngay het han: ");
                String exp = scanner.nextLine();
                inventory.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
                break;
        }
    }

    private static void removeProduct() {
        System.out.print("Nhap ID san pham can xoa: ");
        String id = scanner.nextLine();
        inventory.removeProductById(id);
    }

    private static void updateProduct() {
        System.out.print("Nhap ID san pham can cap nhat: ");
        String id = scanner.nextLine();
        System.out.print("Gia moi: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong moi: ");
        int qty = Integer.parseInt(scanner.nextLine());
        inventory.updateProduct(id, price, qty);
    }

    private static void searchByName() {
        System.out.print("Nhap tu khoa ten: ");
        String name = scanner.nextLine();
        for (Product p : inventory.searchByName(name)) {
            p.display();
        }
    }

    private static void searchByPrice() {
        System.out.print("Gia toi thieu: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Gia toi da: ");
        double max = Double.parseDouble(scanner.nextLine());
        for (Product p : inventory.searchByPriceRange(min, max)) {
            p.display();
        }
    }

    private static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhap ID san pham de them vao don (hoac 'done' de hoan tat): ");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            Product p = inventory.getProductById(id);
            if (p == null) {
                System.out.println("Khong tim thay san pham.");
                continue;
            }
            System.out.print("So luong: ");
            int qty = Integer.parseInt(scanner.nextLine());
            try {
                order.addItem(p, qty);
            } catch (OutOfStockException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
        order.displayOrder();
    }
}
