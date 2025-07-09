import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManager manager = InventoryManager.getInstance();

        while (true) {
            System.out.println("\n===== MENU QUAN LY KHO =====");
            System.out.println("1. Them san pham");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Cap nhat san pham");
            System.out.println("4. Tim kiem san pham");
            System.out.println("5. Hien thi tat ca san pham");
            System.out.println("6. Tao don hang");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Loai san pham (1-Dien tu, 2-Quan ao, 3-Thuc pham): ");
                    int type = sc.nextInt(); sc.nextLine();
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Ten: "); String name = sc.nextLine();
                    System.out.print("Gia: "); double price = sc.nextDouble();
                    System.out.print("Ton kho: "); int stock = sc.nextInt(); sc.nextLine();
                    Product p = null;
                    if (type == 1) {
                        System.out.print("Bao hanh (thang): "); int w = sc.nextInt();
                        System.out.print("Cong suat (W): "); int pow = sc.nextInt(); sc.nextLine();
                        p = new ElectronicProduct(id, name, price, stock, w, pow);
                    } else if (type == 2) {
                        System.out.print("Kich thuoc: "); String size = sc.nextLine();
                        System.out.print("Chat lieu: "); String mat = sc.nextLine();
                        p = new ClothingProduct(id, name, price, stock, size, mat);
                    } else if (type == 3) {
                        System.out.print("Ngay san xuat: "); String mfg = sc.nextLine();
                        System.out.print("Ngay het han: "); String exp = sc.nextLine();
                        p = new FoodProduct(id, name, price, stock, mfg, exp);
                    }
                    if (p != null) manager.addProduct(p);
                    break;
                case 2:
                    System.out.print("Nhap ID san pham can xoa: ");
                    String rid = sc.nextLine();
                    manager.removeProduct(rid);
                    break;
                case 3:
                    System.out.print("Nhap ID san pham can cap nhat: ");
                    String uid = sc.nextLine();
                    System.out.print("Gia moi: ");
                    double nprice = sc.nextDouble();
                    System.out.print("Ton kho moi: ");
                    int nstock = sc.nextInt(); sc.nextLine();
                    manager.updateProduct(uid, nprice, nstock);
                    break;
                case 4:
                    System.out.print("Tim theo (1-Ten, 2-Khoang gia): ");
                    int stype = sc.nextInt(); sc.nextLine();
                    if (stype == 1) {
                        System.out.print("Nhap ten: ");
                        String sname = sc.nextLine();
                        List<Product> found = manager.searchByName(sname);
                        for (Product fp : found) fp.display();
                    } else {
                        System.out.print("Gia toi thieu: ");
                        double min = sc.nextDouble();
                        System.out.print("Gia toi da: ");
                        double max = sc.nextDouble(); sc.nextLine();
                        List<Product> found = manager.searchByPriceRange(min, max);
                        for (Product fp : found) fp.display();
                    }
                    break;
                case 5:
                    manager.displayAll();
                    break;
                case 6:
                    Order order = new Order();
                    while (true) {
                        System.out.print("Nhap ID san pham de mua (hoac 'xong'): ");
                        String oid = sc.nextLine();
                        if (oid.equalsIgnoreCase("xong")) break;
                        Product op = manager.getProductById(oid);
                        if (op == null) {
                            System.out.println("Khong tim thay san pham!");
                            continue;
                        }
                        System.out.print("So luong: ");
                        int oq = sc.nextInt(); sc.nextLine();
                        try {
                            order.addProduct(op, oq);
                        } catch (OutOfStockException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    order.processOrder();
                    order.displayOrder();
                    break;
                case 0:
                    System.out.println("Tam biet!");
                    return;
            }
        }
    }
} 