package Tuan01;

import java.util.*;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static InventoryManager manager = InventoryManager.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== QUAN LY KHO HANG =====");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Tao don hang");
            System.out.println("3. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    productMenu();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    System.out.println("Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void productMenu() {
        while (true) {
            System.out.println("\n--- Quan ly san pham ---");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Xoa san pham");
            System.out.println("3. Cap nhat gia/so luong");
            System.out.println("4. Tim kiem theo ten");
            System.out.println("5. Tim kiem theo khoang gia");
            System.out.println("6. Hien thi tat ca san pham");
            System.out.println("7. Luu kho ra file");
            System.out.println("8. Doc kho tu file");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    searchByPrice();
                    break;
                case 6:
                    manager.displayAll();
                    break;
                case 7:
                    saveInventory();
                    break;
                case 8:
                    loadInventory();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void addProduct() {
        System.out.println("Chon loai san pham: 1-Dien tu, 2-Quan ao, 3-Thuc pham");
        int type = Integer.parseInt(scanner.nextLine());
        // Nếu type khong nam trong 1-3 thi bat nhap lai
        while (type < 1 || type > 3) {
            System.out.println("Lua chon khong hop le!");
            System.out.println("Chon loai san pham: 1-Dien tu, 2-Quan ao, 3-Thuc pham");
            type = Integer.parseInt(scanner.nextLine());
        }
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ten: ");
        String name = scanner.nextLine();
        System.out.print("Gia: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("So luong: ");
        int qty = Integer.parseInt(scanner.nextLine());
        Product p = null;
        switch (type) {
            case 1:
                System.out.print("Bao hanh (thang): ");
                int warranty = Integer.parseInt(scanner.nextLine());
                System.out.print("Cong suat: ");
                String power = scanner.nextLine();
                p = new ElectronicProduct(id, name, price, qty, warranty, power);
                break;
            case 2:
                System.out.print("Size: ");
                String size = scanner.nextLine();
                System.out.print("Chat lieu: ");
                String material = scanner.nextLine();
                p = new ClothingProduct(id, name, price, qty, size, material);
                break;
            case 3:
                System.out.print("Ngay san xuat (yyyy-mm-dd): ");
                LocalDate nsx = LocalDate.parse(scanner.nextLine());
                System.out.print("Han su dung (yyyy-mm-dd): ");
                LocalDate hsd = LocalDate.parse(scanner.nextLine());
                p = new FoodProduct(id, name, price, qty, nsx, hsd);
                break;
            default:
                System.out.println("Loai san pham khong hop le!");
                return;
        }
        manager.addProduct(p);
        System.out.println("Da them san pham!");
    }

    private static void removeProduct() {
        System.out.print("Nhap ID san pham can xoa: ");
        String id = scanner.nextLine();
        if (manager.removeProduct(id)) {
            System.out.println("Da xoa san pham!");
        } else {
            System.out.println("Khong tim thay san pham!");
        }
    }

    private static void updateProduct() {
        System.out.print("Nhap ID san pham can cap nhat: ");
        String id = scanner.nextLine();
        Product p = manager.findById(id);
        if (p == null) {
            System.out.println("Khong tim thay san pham!");
            return;
        }
        System.out.print("Nhap gia moi: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhap so luong moi: ");
        int qty = Integer.parseInt(scanner.nextLine());
        p.setPrice(price);
        p.setQuantityInStock(qty);
        System.out.println("Da cap nhat!");
    }

    private static void searchByName() {
        System.out.print("Nhap ten san pham can tim: ");
        String name = scanner.nextLine();
        List<Product> result = manager.searchByName(name);
        if (result.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            for (Product p : result)
                p.display();
        }
    }

    private static void searchByPrice() {
        System.out.print("Nhap gia thap nhat: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhap gia cao nhat: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Product> result = manager.searchByPrice(min, max);
        if (result.isEmpty()) {
            System.out.println("Khong tim thay san pham!");
        } else {
            for (Product p : result)
                p.display();
        }
    }

    private static void createOrder() {
        Order order = new Order();
        while (true) {
            System.out.print("Nhap ID san pham (hoac 0 de ket thuc): ");
            String id = scanner.nextLine();
            if (id.equals("0"))
                break;
            Product p = manager.findById(id);
            if (p == null) {
                System.out.println("Khong tim thay san pham!");
                continue;
            }
            System.out.print("Nhap so luong mua: ");
            int qty = Integer.parseInt(scanner.nextLine());
            order.addItem(p, qty);
            try {
                order.process();
                System.out.println("Dat hang thanh cong!");
                order.displayOrder();
            } catch (OutOfStockException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }

    }

    private static void saveInventory() {
        try {
            InventoryStorage.saveToFile(manager.getProducts(), "kho.dat");
            System.out.println("Da luu kho ra file kho.dat!");
        } catch (Exception e) {
            System.out.println("Loi khi luu file: " + e.getMessage());
        }
    }

    private static void loadInventory() {
        try {
            List<Product> products = InventoryStorage.loadFromFile("kho.dat");
            manager.setProducts(products);
            System.out.println("Da doc kho tu file kho.dat!");
        } catch (Exception e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
        }
    }
}