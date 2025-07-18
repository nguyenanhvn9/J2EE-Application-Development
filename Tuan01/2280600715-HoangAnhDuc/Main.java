import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance(); // Tải dữ liệu từ file khi khởi tạo
        while (true) {
            System.out.println("\n===== INVENTORY SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Create Order");
            System.out.println("4. Save Data to File"); // Thêm tùy chọn lưu file
            System.out.println("5. Load Data from File"); // Thêm tùy chọn đọc file
            System.out.println("6. Search by Name"); // Thêm tìm kiếm
            System.out.println("7. Filter by Price Range"); // Thêm lọc
            System.out.println("8. Calculate Total Value"); // Thêm tính toán
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> addProductCLI(manager);
                case 2 -> manager.displayAllProducts();
                case 3 -> createOrderCLI(manager);
                case 4 -> saveDataCLI(manager); // Lưu dữ liệu vào file
                case 5 -> loadDataCLI(manager); // Đọc dữ liệu từ file
                case 6 -> searchByNameCLI(manager); // Tìm kiếm theo tên
                case 7 -> filterByPriceRangeCLI(manager); // Lọc theo khoảng giá
                case 8 -> calculateTotalValueCLI(manager); // Tính tổng giá trị
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static int getIntInput() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ.");
            return -1;
        }
    }

    static double getDoubleInput() {
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ.");
            return -1.0;
        }
    }

    static void addProductCLI(InventoryManager manager) {
        System.out.print("Enter product type (1-Elec, 2-Cloth, 3-Food): ");
        int type = getIntInput();
        if (type == -1) return;

        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Price: "); double price = getDoubleInput();
        if (price == -1.0) return;
        System.out.print("Quantity: "); int qty = getIntInput();
        if (qty == -1) return;

        switch (type) {
            case 1 -> {
                System.out.print("Warranty (months): "); int w = getIntInput();
                if (w == -1) return;
                System.out.print("Power (W): "); int p = getIntInput();
                if (p == -1) return;
                manager.addProduct(new ElectronicProduct(id, name, price, qty, w, p));
            }
            case 2 -> {
                System.out.print("Size: "); String size = sc.nextLine();
                System.out.print("Material: "); String mat = sc.nextLine();
                manager.addProduct(new ClothingProduct(id, name, price, qty, size, mat));
            }
            case 3 -> {
                System.out.print("MFG Date (yyyy-mm-dd): ");
                LocalDate mfg = getLocalDateInput();
                System.out.print("EXP Date (yyyy-mm-dd): ");
                LocalDate exp = getLocalDateInput();
                manager.addProduct(new FoodProduct(id, name, price, qty, mfg, exp));
            }
            default -> System.out.println("Invalid product type.");
        }
        System.out.println("Product added.");
    }

    static LocalDate getLocalDateInput() {
        try {
            return LocalDate.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không hợp lệ, dùng yyyy-mm-dd.");
            return null;
        }
    }

    static void createOrderCLI(InventoryManager manager) {
        Order order = new Order();
        while (true) {
            System.out.print("Enter product ID (or 'done'): ");
            String id = sc.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            System.out.print("Enter quantity: ");
            int qty = getIntInput();
            if (qty == -1) continue;

            try {
                order.addItem(id, qty);
            } catch (OutOfStockException e) {
                System.out.println("⚠️ " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
        order.displayOrder();
    }

    static void saveDataCLI(InventoryManager manager) {
        manager.saveData(); // Gọi phương thức lưu dữ liệu
        System.out.println("Data saved to inventory.dat.");
    }

    static void loadDataCLI(InventoryManager manager) {
        manager.loadData(); // Gọi phương thức tải dữ liệu
        System.out.println("Data loaded from inventory.dat.");
    }

    static void searchByNameCLI(InventoryManager manager) {
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine();
        List<Product> results = manager.searchProductsByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No products found.");
        } else {
            results.forEach(Product::display);
        }
    }

    //nhập giá thấp nhất và giá tối đa
    static void filterByPriceRangeCLI(InventoryManager manager) {
        System.out.print("Enter min price: "); double min = getDoubleInput();
        if (min == -1.0) return;
        System.out.print("Enter max price: "); double max = getDoubleInput();
        if (max == -1.0) return;
        List<Product> results = manager.findProductsByPriceRange(min, max);
        if (results.isEmpty()) {
            System.out.println("No products found in this range.");
        } else {
            results.forEach(Product::display);
        }
    }

    static void calculateTotalValueCLI(InventoryManager manager) {
        double total = manager.calculateTotalValue();
        System.out.printf("Total value of inventory: %.2f%n", total);
    }
}