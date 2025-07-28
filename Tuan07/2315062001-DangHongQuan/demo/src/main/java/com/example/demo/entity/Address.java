package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Data // Lombok: Tự động tạo getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Tự động tạo constructor không đối số
@AllArgsConstructor // Lombok: Tự động tạo constructor với tất cả đối số
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- NEW/RENAMED FIELDS TO MATCH DTO AND FORMS ---
    @Column(nullable = false, length = 255) // Tên gợi nhớ (e.g., "Nhà riêng", "Công ty")
    private String alias;

    @Column(name = "recipient_name", nullable = false, length = 255) // Tên người nhận
    private String recipientName;

    @Column(name = "phone_number", nullable = false, length = 20) // Số điện thoại
    private String phoneNumber;

    @Column(name = "street_address", nullable = false, length = 255) // Số nhà, tên đường
    private String streetAddress;

    @Column(nullable = false, length = 100) // Phường/Xã
    private String ward;

    @Column(nullable = false, length = 100) // Quận/Huyện
    private String district;
    // --- END NEW/RENAMED FIELDS ---


    @Column(nullable = false, length = 100) // Tỉnh/Thành phố
    private String city;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Lưu ý: Với @Data của Lombok, bạn không cần viết getters/setters thủ công.
    // Lombok sẽ tự động tạo chúng dựa trên tên trường.
    // Đảm bảo rằng DTO của bạn (AddressDto) cũng có các trường này với tên tương ứng.
}