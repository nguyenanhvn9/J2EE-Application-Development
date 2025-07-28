// package com.example.demo.entity;
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column; // Đảm bảo đã import

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name; // Tên cửa hàng (e.g., "TechShop Hà Nội")

    @Column(nullable = false, length = 500)
    private String address; // Địa chỉ chi tiết của cửa hàng

    @Column(length = 20)
    private String phoneNumber; // Số điện thoại liên hệ của cửa hàng

    @Column(length = 255)
    private String imageUrl; // URL hình ảnh cửa hàng (nếu có)

    @Column(precision = 10) // <-- Đã xóa scale = 7
    private Double latitude;

    @Column(precision = 10) // <-- Đã xóa scale = 7
    private Double longitude;


    // Có thể thêm giờ mở cửa, v.v.
}