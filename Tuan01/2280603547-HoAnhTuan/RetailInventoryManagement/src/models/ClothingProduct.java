package models; // Khai báo package models

public class ClothingProduct extends Product {
    private String size;
    private String material;

    // Constructor của ClothingProduct
    public ClothingProduct(String id, String name, double price, int quantity, String size, String material) {
        super(id, name, price, quantity); // Gọi constructor của lớp cha (Product)
        this.size = size;
        this.material = material;
    }

    // Ghi đè phương thức display() để hiển thị thông tin chi tiết của ClothingProduct
    @Override
    public void display() {
        System.out.printf("Clothing Product   | ID: %s | Name: %s | Price: %.2f | Stock: %d | Size: %s | Material: %s%n",
                          id, name, price, quantityInStock, size, material);
    }

    // Getters cho các thuộc tính riêng của ClothingProduct
    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }
}