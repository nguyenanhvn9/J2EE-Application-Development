import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== QUAN LY KHO HANG =====");
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
                case 1:
                    addProductMenu();
                    break;
                case 2:
                    removeProductMenu();
                    break;
                case 3:
                    updateProductMenu();
                    break;
                case 4:
                    searchByNameMenu();
                    break;
                case 5:
                    searchByPriceMenu();
                    break;
                case 6:
                    inventory.displayAllProducts();
                    break;
                case 7:
                    createOrderMenu();
                    break;
                case 0:
                    System.out.println("Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void addProductMenu() {
        System.out.println("Chon loai san pham:");
        System.out.println("1. Dien tu");
        System.out.println("2. Quan ao");
        System.out.println("3. Thuc pham");
        System.out.print("Lua chon: ");
        int type = Integer.parseInt(scanner.nextLine());
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ten: ");
        String name = scanner.nextLine();
        System.out.print("Gia: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        Product product = null;
        switch (type) {
            case 1:
                System.out.print("Bao hanh (thang): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Cong suat (W): ");
                double power = Double.parseDouble(scanner.nextLine());
                product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Chat lieu: ");
                String material = scanner.nextLine();
                product = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                System.out.print("Ngay san xuat: ");
                String nsx = scanner.nextLine();
                System.out.print("Han su dung: ");
                String hsd = scanner.nextLine();
                product = new FoodProduct(id, name, price, quantity, nsx, hsd);
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
                return;
        }
        inventory.addProduct(product);
        System.out.println("Da them san pham thanh cong!");
    }

    private static void removeProductMenu() {
        System.out.print("Nhap ID san pham can xoa: ");
        String id = scanner.nextLine();
        if (inventory.removeProductById(id)) {
            System.out.println("Da xoa san pham thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham!");
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
            System.out.println("Cap nhat thanh cong!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private static void searchByNameMenu() {
        System.out.print("Nhap ten san pham can tim: ");
        String name = scanner.nextLine();
        List<Product> result = inventory.searchByName(name);
        if (result.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            for (Product p : result) {
                p.display();
            }
        }
    }

    private static void searchByPriceMenu() {
        System.out.print("Nhap gia thap nhat: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhap gia cao nhat: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Product> result = inventory.searchByPriceRange(min, max);
        if (result.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            for (Product p : result) {
                p.display();
            }
        }
    }

    private static void createOrderMenu() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhap ID san pham muon mua (hoac 0 de ket thuc): ");
            String id = scanner.nextLine();
            if (id.equals("0")) break;
            Product product = inventory.getProductById(id);
            if (product == null) {
                System.out.println("Khong tim thay san pham!");
                continue;
            }
            System.out.print("Nhap so luong muon mua: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            try {
                order.addProduct(product, quantity);
                System.out.println("Da them vao don hang!");
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        order.displayOrder();
        order.processOrder();
    }
} 