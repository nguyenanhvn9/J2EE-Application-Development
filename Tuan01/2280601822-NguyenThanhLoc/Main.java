import model.*;
import manager.InventoryManager;
import order.*;
import java.util.*;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager inventory = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = getIntInput("-> Chon chuc nang: ");
            switch (choice) {
                case 1: addProductCLI(); break;
                case 2: removeProductCLI(); break;
                case 3: updateProductCLI(); break;
                case 4: searchByNameCLI(); break;
                case 5: searchByPriceCLI(); break;
                case 6: displayAllProductsCLI(); break;
                case 7: createOrderCLI(); break;
                case 0: System.out.println("Tam biet!"); System.exit(0);
                default: System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==============================");
        System.out.println("  HE THONG QUAN LY KHO HANG");
        System.out.println("==============================");
        System.out.println("1. Them san pham moi");
        System.out.println("2. Xoa san pham theo ID");
        System.out.println("3. Cap nhat thong tin san pham");
        System.out.println("4. Tim kiem san pham theo ten");
        System.out.println("5. Tim kiem san pham theo khoang gia");
        System.out.println("6. Hien thi tat ca san pham");
        System.out.println("7. Tao don hang");
        System.out.println("0. Thoat");
        System.out.println("==============================");
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so hop le!");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void addProductCLI() {
        System.out.println("\nChon loai san pham:");
        System.out.println("  1. Dien tu");
        System.out.println("  2. Quan ao");
        System.out.println("  3. Thuc pham");
        int type = getIntInput("-> Loai san pham (1-3): ");
        String id = getStringInput("-> ID: ");
        String name = getStringInput("-> Ten: ");
        double price = getDoubleInput("-> Gia: ");
        int quantity = getIntInput("-> So luong: ");
        Product p = null;
        switch (type) {
            case 1:
                int warranty = getIntInput("-> Bao hanh (thang): ");
                int power = getIntInput("-> Cong suat (W): ");
                p = new ElectronicProduct(id, name, price, quantity, warranty, power);
                break;
            case 2:
                String size = getStringInput("-> Size: ");
                String material = getStringInput("-> Chat lieu: ");
                p = new ClothingProduct(id, name, price, quantity, size, material);
                break;
            case 3:
                LocalDate nsx = null, hsd = null;
                while (nsx == null) {
                    try {
                        nsx = LocalDate.parse(getStringInput("-> Ngay san xuat (yyyy-MM-dd): "));
                    } catch (Exception e) {
                        System.out.println("  Dinh dang ngay khong hop le!");
                    }
                }
                while (hsd == null) {
                    try {
                        hsd = LocalDate.parse(getStringInput("-> Han su dung (yyyy-MM-dd): "));
                    } catch (Exception e) {
                        System.out.println("  Dinh dang ngay khong hop le!");
                    }
                }
                p = new FoodProduct(id, name, price, quantity, nsx, hsd);
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
                return;
        }
        inventory.addProduct(p);
        System.out.println("Da them san pham!");
    }

    private static void removeProductCLI() {
        String id = getStringInput("-> Nhap ID san pham can xoa: ");
        if (inventory.removeProduct(id)) {
            System.out.println("Da xoa san pham!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private static void updateProductCLI() {
        String id = getStringInput("-> Nhap ID san pham can cap nhat: ");
        double price = getDoubleInput("-> Gia moi: ");
        int quantity = getIntInput("-> So luong moi: ");
        if (inventory.updateProduct(id, price, quantity)) {
            System.out.println("Da cap nhat san pham!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private static void searchByNameCLI() {
        String name = getStringInput("-> Nhap ten san pham can tim: ");
        List<Product> results = inventory.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            printProductTableHeader();
            results.forEach(Main::printProductRow);
        }
    }

    private static void searchByPriceCLI() {
        double min = getDoubleInput("-> Nhap gia thap nhat: ");
        double max = getDoubleInput("-> Nhap gia cao nhat: ");
        List<Product> results = inventory.searchByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            printProductTableHeader();
            results.forEach(Main::printProductRow);
        }
    }

    private static void displayAllProductsCLI() {
        List<Product> all = new ArrayList<>();
        for (Product p : inventory.searchByName("")) all.add(p);
        if (all.isEmpty()) {
            System.out.println("(Kho hang trong)");
        } else {
            printProductTableHeader();
            all.forEach(Main::printProductRow);
        }
    }

    private static void createOrderCLI() {
        Order order = new Order();
        while (true) {
            String id = getStringInput("-> Nhap ID san pham muon mua (hoac 'x' de ket thuc): ");
            if (id.equalsIgnoreCase("x")) break;
            int quantity = getIntInput("-> So luong: ");
            try {
                order.addItem(id, quantity);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            order.processOrder();
            System.out.println("Dat hang thanh cong!");
            order.displayOrder();
        } catch (OutOfStockException e) {
            System.out.println("Dat hang that bai: " + e.getMessage());
        }
    }

    // Hien thi bang san pham
    private static void printProductTableHeader() {
        System.out.printf("%-10s %-20s %-10s %-10s %-20s\n", "ID", "Ten", "Gia", "Ton kho", "Thong tin them");
        System.out.println("---------------------------------------------------------------");
    }
    private static void printProductRow(Product p) {
        String extra = "";
        if (p instanceof ElectronicProduct) {
            ElectronicProduct ep = (ElectronicProduct) p;
            extra = "Bao hanh: " + ep.getWarrantyMonths() + " thang, Cong suat: " + ep.getPower() + "W";
        } else if (p instanceof ClothingProduct) {
            ClothingProduct cp = (ClothingProduct) p;
            extra = "Size: " + cp.getSize() + ", Chat lieu: " + cp.getMaterial();
        } else if (p instanceof FoodProduct) {
            FoodProduct fp = (FoodProduct) p;
            extra = "NSX: " + fp.getManufactureDate() + ", HSD: " + fp.getExpiryDate();
        }
        System.out.printf("%-10s %-20s %-10.2f %-10d %-20s\n", p.getId(), p.getName(), p.getPrice(), p.getQuantityInStock(), extra);
    }
} 