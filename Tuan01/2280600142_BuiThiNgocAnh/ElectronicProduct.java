// Lớp sản phẩm điện tử kế thừa Product
public class ElectronicProduct extends Product {
    // Thời gian bảo hành (tháng)
    private int warrantyMonths;
    // Công suất
    private double power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, double power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    // Getter và Setter
    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }
    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }

    // Hiển thị thông tin sản phẩm điện tử
    @Override
    public void display() {
        System.out.println("[Điện tử] Mã: " + id + ", Tên: " + name + ", Giá: " + price + ", Tồn kho: " + quantityInStock + ", Bảo hành: " + warrantyMonths + " tháng, Công suất: " + power + "W");
    }
} 