import java.time.LocalDate;
// Lớp sản phẩm thực phẩm kế thừa Product
public class FoodProduct extends Product {
    // Ngày sản xuất
    private LocalDate manufactureDate;
    // Ngày hết hạn
    private LocalDate expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, LocalDate manufactureDate, LocalDate expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    // Getter và Setter
    public LocalDate getManufactureDate() { return manufactureDate; }
    public void setManufactureDate(LocalDate manufactureDate) { this.manufactureDate = manufactureDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    // Hiển thị thông tin sản phẩm thực phẩm
    @Override
    public void display() {
        System.out.println("[Thực phẩm] Mã: " + id + ", Tên: " + name + ", Giá: " + price + ", Tồn kho: " + quantityInStock + ", NSX: " + manufactureDate + ", HSD: " + expiryDate);
    }
} 