package retailinventorymanagement.service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import retailinventorymanagement.model.ClothingProduct;
import retailinventorymanagement.model.ElectronicProduct;
import retailinventorymanagement.model.FoodProduct;
import retailinventorymanagement.model.Product;

public class InventoryManager {

    private static InventoryManager instance;
    private Map<String, Product> products;
    private static final String FILE_PATH = "inventory.dat";

    // Constructor private để đảm bảo singleton
    private InventoryManager() {
        products = new HashMap<>();
        loadFromCSV("inventory.csv"); // Load dữ liệu khi khởi tạo
    }

    // Singleton pattern
    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // Thêm sản phẩm vào kho
    public void addProduct(Product product) {
        products.put(product.getId(), product);
        System.out.println("✅ Đã thêm sản phẩm: " + product.getName());
    }

// Xóa sản phẩm theo ID
    public void removeProduct(String id) {
        Product removed = products.remove(id);
        if (removed != null) {
            System.out.println("✅ Đã xóa sản phẩm: " + removed.getName());
        } else {
            System.out.println("⚠️ Không tìm thấy sản phẩm với ID: " + id);
        }
    }

// Cập nhật giá và số lượng sản phẩm
    public void updateProduct(String id, double newPrice, int newQty) {
        Product product = products.get(id);
        if (product != null) {
            product.setPrice(newPrice);
            product.setQuantityInStock(newQty);
            System.out.println("✅ Đã cập nhật sản phẩm: " + product.getName());
        } else {
            System.out.println("⚠️ Không tìm thấy sản phẩm để cập nhật với ID: " + id);
        }
    }

// Tìm sản phẩm theo tên
    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        System.out.println("🔍 Tìm thấy " + result.size() + " sản phẩm theo tên: \"" + name + "\"");
        return result;
    }

// Tìm sản phẩm theo khoảng giá
    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        System.out.println("🔍 Tìm thấy " + result.size() + " sản phẩm trong khoảng giá từ " + min + " đến " + max);
        return result;
    }

// Hiển thị tất cả sản phẩm
    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("📦 Kho hàng hiện đang trống.");
        } else {
            System.out.println("📦 Danh sách sản phẩm hiện có:");
            for (Product p : products.values()) {
                p.display();
            }
        }
    }

// Lấy sản phẩm theo ID
    public Product getProduct(String id) {
        Product p = products.get(id);
        if (p != null) {
            System.out.println("✅ Đã tìm thấy sản phẩm: " + p.getName());
        } else {
            System.out.println("⚠️ Không tìm thấy sản phẩm với ID: " + id);
        }
        return p;
    }

    // Lưu dữ liệu ra file
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(products);
            System.out.println("✅ Đã lưu dữ liệu kho hàng.");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    // Nạp dữ liệu từ file
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            products = (Map<String, Product>) ois.readObject();
            System.out.println("✅ Đã nạp dữ liệu từ file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
    }

    // Tính tổng giá trị hàng tồn kho
    public double calculateTotalValue() {
        return products.values().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantityInStock())
                .sum();
    }

    // Lấy danh sách top sản phẩm tồn kho nhiều nhất
    public List<Product> getTopStocked(int topN) {
        return products.values().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getQuantityInStock(), p1.getQuantityInStock()))
                .limit(topN)
                .toList();
    }

    // Lưu dữ liệu kho hàng ra file CSV
    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID,Name,Price,Quantity,Type,ExtraInfo"); // Header

            for (Product p : products.values()) {
                StringBuilder line = new StringBuilder();
                line.append(p.getId()).append(",");
                line.append(p.getName()).append(",");
                line.append(p.getPrice()).append(",");
                line.append(p.getQuantityInStock()).append(",");
                line.append(p.getClass().getSimpleName()).append(",");

                // Tùy loại sản phẩm mà thêm info khác nhau
                if (p instanceof ElectronicProduct ep) {
                    line.append("Warranty: ").append(ep.getWarrantyMonths())
                            .append(" months, Power: ").append(ep.getPower());
                } else if (p instanceof ClothingProduct cp) {
                    line.append("Size: ").append(cp.getSize())
                            .append(", Material: ").append(cp.getMaterial());
                } else if (p instanceof FoodProduct fp) {
                    line.append("MFG: ").append(fp.getManufactureDate())
                            .append(", EXP: ").append(fp.getExpiryDate());
                }

                writer.println(line.toString());
            }

            System.out.println("✅ Đã lưu kho hàng ra file CSV: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi lưu CSV: " + e.getMessage());
        }
    }

    public void loadFromCSV(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("❌ File không tồn tại: " + filename);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;

            br.readLine(); // Bỏ qua dòng tiêu đề (header)

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 6); // Tách thành 6 phần: ID, Name, Price, Quantity, Type, ExtraInfo

                if (parts.length < 6) {
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                int quantity = Integer.parseInt(parts[3].trim());
                String type = parts[4].trim();
                String extra = parts[5].trim();

                Product product = null;

                switch (type) {
                    case "ElectronicProduct" -> {
                        // Parse extra info: "Warranty: 12 months, Power: 220W"
                        int warranty = 0;
                        int power = 0;
                        try {
                            String[] extraParts = extra.split(",");
                            warranty = Integer.parseInt(extraParts[0].replaceAll("[^0-9]", ""));
                        } catch (NumberFormatException ignored) {
                        }
                        product = new ElectronicProduct(id, name, price, quantity, warranty, power);
                    }

                    case "ClothingProduct" -> {
                        // "Size: M, Material: Cotton"
                        String size = "";
                        String material = "";
                        try {
                            String[] extraParts = extra.split(",");
                            size = extraParts[0].split(":")[1].trim();
                            material = extraParts[1].split(":")[1].trim();
                        } catch (Exception ignored) {
                        }
                        product = new ClothingProduct(id, name, price, quantity, size, material);
                    }

                    case "FoodProduct" -> {
                        // "MFG: 2024-06-01, EXP: 2025-06-01"
                        LocalDate mfg = LocalDate.now();
                        LocalDate exp = LocalDate.now();
                        try {
                            String[] extraParts = extra.split(",");
                            mfg = LocalDate.parse(extraParts[0].split(":")[1].trim());
                            exp = LocalDate.parse(extraParts[1].split(":")[1].trim());
                        } catch (Exception ignored) {
                        }
                        product = new FoodProduct(id, name, price, quantity, mfg, exp);
                    }
                }

                if (product != null) {
                    products.put(id, product);
                    count++;
                }
            }

            System.out.println("✅ Đã nạp " + count + " sản phẩm từ file CSV.");
        } catch (IOException e) {
            System.out.println("❌ Lỗi khi đọc CSV: " + e.getMessage());
        }
    }

}
