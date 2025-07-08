import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Hien thi danh sach san pham");
            System.out.println("4. Tao don hang");
            System.out.println("5. Cap nhat san pham");
            System.out.println("6. Tim kiem san pham theo ten");
            System.out.println("7. Tim kiem san pham theo khoang gia");
            System.out.println("8. Tinh tong gia tri kho hang");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Chon loai san pham: 1. Dien tu 2. Quan ao 3. Thuc pham");
                    int type = sc.nextInt();
                    sc.nextLine();
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Ten: "); String name = sc.nextLine();
                    System.out.print("Gia: "); double price = sc.nextDouble();
                    System.out.print("So luong: "); int qty = sc.nextInt();
                    sc.nextLine();
                    Product p = null;
                    if (type == 1) {
                        System.out.print("Bao hanh (thang): "); int bh = sc.nextInt();
                        System.out.print("Cong suat: "); int cs = sc.nextInt();
                        p = new ElectronicProduct(id, name, price, qty, bh, cs);
                    } else if (type == 2) {
                        sc.nextLine();
                        System.out.print("Size: "); String size = sc.nextLine();
                        System.out.print("Chat lieu: "); String material = sc.nextLine();
                        p = new ClothingProduct(id, name, price, qty, size, material);
                    } else if (type == 3) {
                        sc.nextLine();
                        System.out.print("Ngay san xuat (yyyy-mm-dd): "); String nsx = sc.nextLine();
                        System.out.print("Han su dung (yyyy-mm-dd): "); String hsd = sc.nextLine();
                        p = new FoodProduct(id, name, price, qty, LocalDate.parse(nsx), LocalDate.parse(hsd));
                    }
                    if (p != null) manager.addProduct(p);
                    break;
                case 2:
                    System.out.print("Nhap ID san pham can xoa: ");
                    String removeId = sc.nextLine();
                    manager.removeProduct(removeId);
                    break;
                case 3:
                    manager.displayAll();
                    break;
                case 4:
                    Order order = new Order();
                    while (true) {
                        System.out.print("Nhap ID san pham muon mua (hoac 'x' de ket thuc): ");
                        String oid = sc.nextLine();
                        if (oid.equals("x")) break;
                        Product op = manager.getProduct(oid);
                        if (op == null) {
                            System.out.println("Khong tim thay san pham!");
                            continue;
                        }
                        System.out.print("So luong: ");
                        int oqty = sc.nextInt();
                        sc.nextLine();
                        try {
                            order.addItem(op, oqty);
                        } catch (OutOfStockException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    order.displayOrder();
                    break;
                case 5:
                    System.out.print("Nhap ID san pham can cap nhat: ");
                    String updateId = sc.nextLine();
                    Product updateP = manager.getProduct(updateId);
                    if (updateP == null) {
                        System.out.println("Khong tim thay san pham!");
                        break;
                    }
                    System.out.print("Nhap gia moi: ");
                    double newPrice = sc.nextDouble();
                    System.out.print("Nhap so luong moi: ");
                    int newQty = sc.nextInt();
                    sc.nextLine();
                    updateP.price = newPrice;
                    updateP.quantityInStock = newQty;
                    System.out.println("Da cap nhat san pham.");
                    break;
                case 6:
                    System.out.print("Nhap ten san pham can tim: ");
                    String searchName = sc.nextLine();
                    var foundByName = manager.searchByName(searchName);
                    if (foundByName.isEmpty()) {
                        System.out.println("Khong tim thay san pham nao.");
                    } else {
                        foundByName.forEach(Product::display);
                    }
                    break;
                case 7:
                    System.out.print("Nhap gia thap nhat: ");
                    double minPrice = sc.nextDouble();
                    System.out.print("Nhap gia cao nhat: ");
                    double maxPrice = sc.nextDouble();
                    sc.nextLine();
                    var foundByPrice = manager.searchByPriceRange(minPrice, maxPrice);
                    if (foundByPrice.isEmpty()) {
                        System.out.println("Khong tim thay san pham nao.");
                    } else {
                        foundByPrice.forEach(Product::display);
                    }
                    break;
                case 8:
                    double totalValue = manager.getAllProducts().stream()
                        .mapToDouble(prod -> prod.price * prod.quantityInStock)
                        .sum();
                    System.out.println("Tong gia tri kho hang: " + totalValue);
                    break;
                case 0:
                    return;
            }
        }
    }
}