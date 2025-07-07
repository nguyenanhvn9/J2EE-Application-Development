import manager.InventoryManager;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    InventoryManager manager = InventoryManager.getInstance();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("\n--- QUẢN LÝ KHO BÁN LẺ ---");
      System.out.println("1. Thêm sản phẩm");
      System.out.println("2. Xóa sản phẩm");
      System.out.println("3. Cập nhật thông tin sản phẩm");
      System.out.println("4. Tìm kiếm sản phẩm");
      System.out.println("5. Hiển thị tất cả sản phẩm");
      System.out.println("6. Tạo đơn hàng");
      System.out.println("0. Thoát");
      System.out.print("Chọn chức năng: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          manager.addProductCLI(scanner);
          break;
        case 2:
          manager.removeProductById(scanner);
          break;
        case 3:
          manager.updateProductCLI(scanner);
          break;
        case 4:
          manager.searchProductCLI(scanner);
          break;
        case 5:
          manager.displayAll();
          break;
        case 6:
          manager.createOrderCLI(scanner);
          break;
        case 0:
          System.out.println("Tạm biệt!");
          return;
        default:
          System.out.println("Chọn sai, vui lòng nhập lại.");
      }
    }
  }
}
