package com.example;

public class Main {
    public static void main(String[] args) {
        InventoryManager manager = InventoryManager.getInstance();

        // Thêm sản phẩm
        manager.addProduct(new ElectronicProduct("E001", "Laptop", 1500, 5, 24));
        manager.addProduct(new ClothingProduct("C001", "Áo thun", 250, 20, "L", "Cotton"));
        manager.addProduct(new FoodProduct("F001", "Táo Mỹ", 30, 100, "2025-01-01"));

        System.out.println("\n--- Hiển thị toàn bộ sản phẩm ---");
        manager.displayAllProducts();

        System.out.println("\n--- Cập nhật sản phẩm E001 ---");
        manager.updateProduct("E001", 1400, 8);

        System.out.println("\n--- Tìm kiếm theo tên 'áo' ---");
        manager.searchByName("áo");

        System.out.println("\n--- Tìm kiếm theo khoảng giá 20 - 500 ---");
        manager.searchByPriceRange(20, 500);

        System.out.println("\n--- Xoá sản phẩm F001 ---");
        manager.removeProductById("F001");

        System.out.println("\n--- Danh sách sau khi xoá ---");
        manager.displayAllProducts();
    }
}
