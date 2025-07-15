package Tuan01;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        products = new ArrayList<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product){
        products.add(product);
        System.out.println("Da them san pham " + product.getName() + "thanh cong");
    }

    public void removeProduct(String id){
        products.removeIf(p -> p.getId().equals(id));
        System.out.println("Da xoa san pham co ID:" + id + " thanh cong");
    }

    public void updateProduct(String id, double newPrice, int newQuantity){
        for (Product product : products){
            if(product.getId().equals(id)){
                product.setPrice(newPrice);
                product.setQuantityInStock(newQuantity);
                System.out.println("Da cap nhat san pham co ID: " + id + " thanh cong");
                return;
            }
        }
        System.out.println("Khong tim thay san pham co ID: " + id);
    }

    public void displayProducts() {
        System.out.println("Danh sach san pham");
        for(Product product : products) {
            product.displayInfo();
            System.out.println("-------------------------");
        }

    }
    public void findProductByName(String keyword){
        System.out.println("Tim kiem san pham co ten: " + keyword);
        for (Product product : products){
            if(product.name.toLowerCase().contains(keyword.toLowerCase())){
                product.displayInfo();
            }

        }
    }

    public void findProductByPrice(double minPrice, double maxPrice){
        System.out.println("Tim kiem san pham co gia tu " + minPrice + " den " + maxPrice);
        for (Product product : products){
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice){
                product.displayInfo();
            }
        }
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
