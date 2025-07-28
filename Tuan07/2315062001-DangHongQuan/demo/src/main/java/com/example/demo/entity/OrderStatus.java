package com.example.demo.entity;

public enum OrderStatus {
    PENDING("Đang chờ xử lý"),
    PROCESSING("Đang xử lý"),
    SHIPPED("Đang vận chuyển"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy");

    private final String description; // 1. Thêm trường này để lưu mô tả

    // 2. Thêm constructor để gán mô tả khi khởi tạo enum
    OrderStatus(String description) {
        this.description = description;
    }

    // 3. Thêm phương thức getter công khai cho 'description'
    public String getDescription() {
        return description;
    }
}