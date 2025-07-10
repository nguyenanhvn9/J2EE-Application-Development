package manager;

import model.*;
import order.Order;

import java.time.LocalDate;
import java.util.*;

public class InventoryManager {
  private static InventoryManager instance;
  private Map<String, Product> inventory = new HashMap<>();

  private InventoryManager() {
  }

  public static InventoryManager getInstance() {
    if (instance == null) {
      instance = new InventoryManager();
    }
    return instance;
  }

  public void addProductCLI(Scanner scanner) {
    System.out.println("Chọn loại sản phẩm (1. Điện tử, 2. Quần áo, 3. Thực phẩm): ");
    int type = Integer.parseInt(scanner.nextLine());

    System.out.print("ID: ");
    String id = scanner.nextLine();
    System.out.print("Tên: ");
    String name = scanner.nextLine();
    System.out.print("Giá: ");
    double price = Double.parseDouble(scanner.nextLine());
    System.out.print("Số lượng: ");
    int quantity = Integer.parseInt(scanner.nextLine());

    Product product = null;

    switch (type) {
      case 1:
        System.out.print("Thời gian bảo hành (tháng): ");
        int warranty = Integer.parseInt(scanner.nextLine());
        System.out.print("Công suất (W): ");
        int power = Integer.parseInt(scanner.nextLine());
        product = new ElectronicProduct(id, name, price, quantity, warranty, power);
        break;
      case 2:
        System.out.print("Size: ");
        String size = scanner.nextLine();
        System.out.print("Chất liệu: ");
        String material = scanner.nextLine();
        product = new ClothingProduct(id, name, price, quantity, size, material);
        break;
      case 3:
        System.out.print("Ngày sản xuất (yyyy-mm-dd): ");
        LocalDate mfg = LocalDate.parse(scanner.nextLine());
        System.out.print("Hạn sử dụng (yyyy-mm-dd): ");
        LocalDate exp = LocalDate.parse(scanner.nextLine());
        product = new FoodProduct(id, name, price, quantity, mfg, exp);
        break;
      default:
        System.out.println("Loại sản phẩm không hợp lệ!");
        return;
    }

    inventory.put(id, product);
    System.out.println("✅ Đã thêm sản phẩm thành công.");
  }

  public void removeProductById(Scanner scanner) {
    System.out.print("Nhập ID sản phẩm cần xóa: ");
    String id = scanner.nextLine();
    if (inventory.remove(id) != null) {
      System.out.println("✅ Đã xóa sản phẩm.");
    } else {
      System.out.println("❌ Không tìm thấy sản phẩm.");
    }
  }

  public void updateProductCLI(Scanner scanner) {
    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
    String id = scanner.nextLine();
    Product product = inventory.get(id);
    if (product != null) {
      System.out.print("Giá mới: ");
      double price = Double.parseDouble(scanner.nextLine());
      System.out.print("Số lượng mới: ");
      int qty = Integer.parseInt(scanner.nextLine());
      product.setPrice(price);
      product.setQuantityInStock(qty);
      System.out.println("✅ Đã cập nhật.");
    } else {
      System.out.println("❌ Không tìm thấy sản phẩm.");
    }
  }

  public void searchProductCLI(Scanner scanner) {
    System.out.println("1. Tìm theo tên | 2. Tìm theo khoảng giá");
    int option = Integer.parseInt(scanner.nextLine());
    if (option == 1) {
      System.out.print("Nhập tên: ");
      String name = scanner.nextLine().toLowerCase();
      inventory.values().stream()
          .filter(p -> p.getName().toLowerCase().contains(name))
          .forEach(Product::display);
    } else if (option == 2) {
      System.out.print("Giá min: ");
      double min = Double.parseDouble(scanner.nextLine());
      System.out.print("Giá max: ");
      double max = Double.parseDouble(scanner.nextLine());
      inventory.values().stream()
          .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
          .forEach(Product::display);
    } else {
      System.out.println("❌ Lựa chọn không hợp lệ.");
    }
  }

  public void displayAll() {
    if (inventory.isEmpty()) {
      System.out.println("📦 Kho hàng trống.");
      return;
    }
    for (Product product : inventory.values()) {
      product.display();
    }
  }

  public void createOrderCLI(Scanner scanner) {
    Order order = new Order();
    while (true) {
      System.out.print("Nhập ID sản phẩm muốn mua (hoặc 'done' để hoàn tất): ");
      String id = scanner.nextLine();
      if (id.equalsIgnoreCase("done"))
        break;

      System.out.print("Số lượng: ");
      int qty = Integer.parseInt(scanner.nextLine());

      try {
        order.addProductToOrder(id, qty);
      } catch (OutOfStockException e) {
        System.out.println("❌ Lỗi: " + e.getMessage());
      }
    }
    order.processOrder();
  }

  public Product getProductById(String id) {
    return inventory.get(id);
  }
}
