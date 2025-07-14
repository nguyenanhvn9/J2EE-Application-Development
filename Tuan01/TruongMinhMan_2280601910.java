// File: Tuan01.java
import java.util.*;
import java.util.stream.Collectors;

// Abstract Product class
abstract class Product {
    protected String id;
    protected String name;
    protected double price;
    protected int quantityInStock;

    public Product(String id, String name, double price, int quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public abstract void display();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}

// Subclasses
class ElectronicProduct extends Product {
    private int warrantyMonths;
    private int power;

    public ElectronicProduct(String id, String name, double price, int quantityInStock, int warrantyMonths, int power) {
        super(id, name, price, quantityInStock);
        this.warrantyMonths = warrantyMonths;
        this.power = power;
    }

    @Override
    public void display() {
        System.out.println("San pham Dien tu [Ma: " + id + ", Ten: " + name + ", Gia: " + price +
                ", Ton kho: " + quantityInStock + ", Bao hanh: " + warrantyMonths + " thang, Cong suat: " + power + "W]");
    }
}

class ClothingProduct extends Product {
    private String size;
    private String material;

    public ClothingProduct(String id, String name, double price, int quantityInStock, String size, String material) {
        super(id, name, price, quantityInStock);
        this.size = size;
        this.material = material;
    }

    @Override
    public void display() {
        System.out.println("San pham Quan ao [Ma: " + id + ", Ten: " + name + ", Gia: " + price +
                ", Ton kho: " + quantityInStock + ", Kich co: " + size + ", Chat lieu: " + material + "]");
    }
}

class FoodProduct extends Product {
    private String manufactureDate;
    private String expiryDate;

    public FoodProduct(String id, String name, double price, int quantityInStock, String manufactureDate, String expiryDate) {
        super(id, name, price, quantityInStock);
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    @Override
    public void display() {
        System.out.println("San pham Thuc pham [Ma: " + id + ", Ten: " + name + ", Gia: " + price +
                ", Ton kho: " + quantityInStock + ", Ngay san xuat: " + manufactureDate + ", Han su dung: " + expiryDate + "]");
    }
}

// InventoryManager class
class InventoryManager {
    private static InventoryManager instance;
    Map<String, Product> products;
    
        private InventoryManager() {
            products = new HashMap<>();
        }
    
        public static InventoryManager getInstance() {
            if (instance == null) {
                instance = new InventoryManager();
            }
            return instance;
        }
    
        public void addProduct(Product product) {
            products.put(product.getId(), product);
        }
    
        public void removeProduct(String id) {
            products.remove(id);
        }
    
        public void updateProduct(String id, double price, int quantity) {
            Product product = products.get(id);
            if (product != null) {
                product.setPrice(price);
                product.setQuantityInStock(quantity);
            }
        }
    
        public List<Product> searchByName(String name) {
            return products.values().stream()
                    .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
    
        public List<Product> searchByPriceRange(double minPrice, double maxPrice) {
            return products.values().stream()
                    .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
    
        public void displayAllProducts() {
            products.values().forEach(Product::display);
        }
    }
    
    // Order class
    class Order {
        private List<Product> products;
    
        public Order() {
            products = new ArrayList<>();
        }
    
        public void addProduct(Product product, int quantity) throws OutOfStockException {
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("San pham " + product.getName() + " da het hang!");
            }
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
            products.add(product);
        }
    
        public void displayOrder() {
            System.out.println("Chi tiet Don hang:");
            products.forEach(Product::display);
        }
    }
    
    // Custom Exception
    class OutOfStockException extends Exception {
        public OutOfStockException(String message) {
            super(message);
        }
    }
    
    // Main class with CLI
    public class TruongMinhMan_2280601910 {
        public static void main(String[] args) {
            InventoryManager inventoryManager = InventoryManager.getInstance();
            Scanner scanner = new Scanner(System.in);
    
            while (true) {
                System.out.println("\n--- He thong Quan ly Kho hang ---");
                System.out.println("1. Them San pham");
                System.out.println("2. Xoa San pham");
                System.out.println("3. Cap nhat San pham");
                System.out.println("4. Tim kiem San pham theo Ten");
                System.out.println("5. Tim kiem San pham theo Khoang gia");
                System.out.println("6. Hien thi Tat ca San pham");
                System.out.println("7. Tao Don hang");
                System.out.println("8. Thoat");
                System.out.print("Chon mot tuy chon: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Nhap Loai san pham (1: Dien tu, 2: Quan ao, 3: Thuc pham): ");
                            int type = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Nhap Ma: ");
                            String id = scanner.nextLine();
                            System.out.print("Nhap Ten: ");
                            String name = scanner.nextLine();
                            System.out.print("Nhap Gia: ");
                            double price = scanner.nextDouble();
                            System.out.print("Nhap So luong: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine();
    
                            if (type == 1) {
                                System.out.print("Nhap Bao hanh (thang): ");
                                int warranty = scanner.nextInt();
                                System.out.print("Nhap Cong suat (W): ");
                                int power = scanner.nextInt();
                                scanner.nextLine();
                                inventoryManager.addProduct(new ElectronicProduct(id, name, price, quantity, warranty, power));
                            } else if (type == 2) {
                                System.out.print("Nhap Kich co: ");
                                String size = scanner.nextLine();
                                System.out.print("Nhap Chat lieu: ");
                                String material = scanner.nextLine();
                                inventoryManager.addProduct(new ClothingProduct(id, name, price, quantity, size, material));
                            } else if (type == 3) {
                                System.out.print("Nhap Ngay san xuat: ");
                                String manufactureDate = scanner.nextLine();
                                System.out.print("Nhap Han su dung: ");
                                String expiryDate = scanner.nextLine();
                                inventoryManager.addProduct(new FoodProduct(id, name, price, quantity, manufactureDate, expiryDate));
                            }
                            break;
                        case 2:
                            System.out.print("Nhap Ma san pham can Xoa: ");
                            String removeId = scanner.nextLine();
                            inventoryManager.removeProduct(removeId);
                            break;
                        case 3:
                            System.out.print("Nhap Ma san pham can Cap nhat: ");
                            String updateId = scanner.nextLine();
                            System.out.print("Nhap Gia moi: ");
                            double newPrice = scanner.nextDouble();
                            System.out.print("Nhap So luong moi: ");
                            int newQuantity = scanner.nextInt();
                            scanner.nextLine();
                            inventoryManager.updateProduct(updateId, newPrice, newQuantity);
                            break;
                        case 4:
                            System.out.print("Nhap Ten san pham can Tim kiem: ");
                            String searchName = scanner.nextLine();
                            List<Product> foundByName = inventoryManager.searchByName(searchName);
                            foundByName.forEach(Product::display);
                            break;
                        case 5:
                            System.out.print("Nhap Gia toi thieu: ");
                            double minPrice = scanner.nextDouble();
                            System.out.print("Nhap Gia toi da: ");
                            double maxPrice = scanner.nextDouble();
                            scanner.nextLine();
                            List<Product> foundByPrice = inventoryManager.searchByPriceRange(minPrice, maxPrice);
                            foundByPrice.forEach(Product::display);
                            break;
                        case 6:
                            inventoryManager.displayAllProducts();
                            break;
                        case 7:
                            Order order = new Order();
                            while (true) {
                                System.out.print("Nhap Ma san pham de Them vao Don hang (hoac 'done' de hoan tat): ");
                                String orderId = scanner.nextLine();
                                if (orderId.equalsIgnoreCase("done")) break;
                                Product product = inventoryManager.products.get(orderId);
                            if (product == null) {
                                System.out.println("Khong tim thay san pham!");
                                continue;
                            }
                            System.out.print("Nhap So luong: ");
                            int orderQuantity = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                order.addProduct(product, orderQuantity);
                            } catch (OutOfStockException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        order.displayOrder();
                        break;
                    case 8:
                        System.out.println("Thoat...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Tuy chon khong hop le! Vui long thu lai.");
                }
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }
}