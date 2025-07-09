import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static InventoryManager instance; // Biến tĩnh để lưu instance duy nhất (Singleton)
    private List<Product> products; // Danh sách lưu trữ các sản phẩm

    private InventoryManager() {
        products = new ArrayList<>(); // Khởi tạo danh sách sản phẩm rỗng
        loadData(); // Tải dữ liệu từ file khi khởi tạo
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager(); // Tạo instance mới nếu chưa tồn tại
        }
        return instance; // Trả về instance duy nhất
    }

    public void addProduct(Product product) {
        products.add(product); // Thêm sản phẩm vào danh sách
        saveData(); // Lưu dữ liệu vào file sau khi thêm
    }

    public void removeProduct(String id) {
        products.removeIf(p -> p.getId().equals(id)); // Xóa sản phẩm dựa trên ID
        saveData(); // Lưu lại dữ liệu sau khi xóa
    }

    public void updateProduct(String id, double price, int quantity) {
        products.stream()
                .filter(p -> p.getId().equals(id)) // Lọc sản phẩm theo ID
                .findFirst() // Lấy sản phẩm đầu tiên khớp
                .ifPresent(p -> {
                    p.setQuantityInStock(quantity); // Cập nhật số lượng
                    saveData(); // Lưu dữ liệu sau khi cập nhật
                });
    }

    public Product findProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)) // Lọc sản phẩm theo ID
                .findFirst() // Lấy sản phẩm đầu tiên khớp
                .orElse(null); // Trả về null nếu không tìm thấy
    }

    public void displayAllProducts() {
        products.forEach(Product::display); // Hiển thị thông tin tất cả sản phẩm
    }

    public  void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inventory.dat"))) {
            oos.writeObject(products); // Ghi danh sách sản phẩm vào file nhị phân
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage()); // Xử lý lỗi khi lưu
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File("inventory.dat"); // Tạo đối tượng file
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject(); // Đọc dữ liệu từ file và gán vào products
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data: " + e.getMessage()); // Xử lý lỗi khi tải
            }
        }
    }

    public double calculateTotalValue() {
        return products.stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantityInStock()) // Tính giá trị từng sản phẩm
                .sum(); // Tính tổng giá trị kho hàng
    }

    public List<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
        return products.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice) // Lọc sản phẩm trong khoảng giá
                .toList(); // Trả về danh sách sản phẩm khớp
    }

    public List<Product> searchProductsByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase())) // Tìm kiếm sản phẩm theo tên
                .toList(); // Trả về danh sách sản phẩm khớp
    }   
}