import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== MENU CHINH ===");
            System.out.println("1. Quan ly san pham");
            System.out.println("2. Tao don hang");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    while (true) {
                        System.out.println("=== QUAN LY SAN PHAM ===");
                        System.out.println("1. Them san pham");
                        System.out.println("2. Xoa san pham");
                        System.out.println("3. Cap nhat san pham");
                        System.out.println("4. Tim theo ten");
                        System.out.println("5. Tim theo khoang gia");
                        System.out.println("6. Hien thi tat ca san pham");
                        System.out.println("0. Quay lai");
                        System.out.print("Chon: ");
                        String subChoice = scanner.nextLine();

                        switch (subChoice) {
                            case "1":
                                System.out.print("Loai san pham (1: Dien tu, 2: Quan ao, 3: Thuc pham): ");
                                String type = scanner.nextLine();
                                System.out.print("ID: ");
                                String id = scanner.nextLine();
                                System.out.print("Ten: ");
                                String name = scanner.nextLine();
                                System.out.print("Gia: ");
                                double price = Double.parseDouble(scanner.nextLine());
                                System.out.print("So luong: ");
                                int qty = Integer.parseInt(scanner.nextLine());

                                if (type.equals("1")) {
                                    System.out.print("Bao hanh (thang): ");
                                    int warranty = Integer.parseInt(scanner.nextLine());
                                    System.out.print("Cong suat (W): ");
                                    int power = Integer.parseInt(scanner.nextLine());
                                    manager.addProduct(new ElectronicProduct(id, name, price, qty, warranty, power));
                                } else if (type.equals("2")) {
                                    System.out.print("Size: ");
                                    String size = scanner.nextLine();
                                    System.out.print("Chat lieu: ");
                                    String material = scanner.nextLine();
                                    manager.addProduct(new ClothingProduct(id, name, price, qty, size, material));
                                } else if (type.equals("3")) {
                                    System.out.print("Ngay san xuat (yyyy-mm-dd): ");
                                    String nsx = scanner.nextLine();
                                    System.out.print("HSD (yyyy-mm-dd): ");
                                    String hsd = scanner.nextLine();
                                    manager.addProduct(new FoodProduct(id, name, price, qty,
                                            java.time.LocalDate.parse(nsx), java.time.LocalDate.parse(hsd)));
                                } else {
                                    System.out.println("Loai san pham khong hop le!");
                                }
                                break;
                            case "2":
                                System.out.print("Nhap ID can xoa: ");
                                manager.removeProduct(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Nhap ID can cap nhat: ");
                                String idToUpdate = scanner.nextLine();
                                System.out.print("Gia moi: ");
                                double newPrice = Double.parseDouble(scanner.nextLine());
                                System.out.print("So luong moi: ");
                                int newQty = Integer.parseInt(scanner.nextLine());
                                manager.updateProduct(idToUpdate, newPrice, newQty);
                                break;
                            case "4":
                                System.out.print("Nhap tu khoa ten: ");
                                String keyword = scanner.nextLine();
                                for (Product p : manager.searchByName(keyword)) {
                                    p.display();
                                }
                                break;
                            case "5":
                                System.out.print("Gia tu: ");
                                double min = Double.parseDouble(scanner.nextLine());
                                System.out.print("Gia den: ");
                                double max = Double.parseDouble(scanner.nextLine());
                                for (Product p : manager.searchByPriceRange(min, max)) {
                                    p.display();
                                }
                                break;
                            case "6":
                                manager.displayAll();
                                break;
                            case "0":
                                break;
                            default:
                                System.out.println("Lua chon khong hop le.");
                        }

                        if (subChoice.equals("0")) break;
                    }
                    break;

                case "2":
                    Order order = new Order();
                    System.out.print("Nhap ID san pham (hoac 'x' de thoat): ");
                    String id = scanner.nextLine();
                    if (id.equalsIgnoreCase("x")) break;

                    Product p = manager.getProduct(id);
                    if (p == null) {
                        System.out.println("Khong tim thay san pham!");
                        break;
                    }

                    System.out.print("So luong: ");
                    int qty = Integer.parseInt(scanner.nextLine());

                    try {
                        order.addItem(p, qty);
                        System.out.println("==> Don hang tao thanh cong!");
                        order.displayOrder();
                    } catch (OutOfStockException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "0":
                    System.out.println("Tam biet!");
                    return;

                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
}
