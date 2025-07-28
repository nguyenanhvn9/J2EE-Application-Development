// src/main/java/com/example/demo/entity/Voucher.java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // Mã giảm giá, ví dụ: "SALE2025"

    @Column(nullable = false)
    private String description; // Mô tả voucher

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // LOAI_GIAM_GIA (PERCENTAGE, FIXED_AMOUNT)

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue; // Giá trị giảm (VD: 10.00 cho 10% hoặc 50000 cho 50,000 VND)

    @Column(precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount; // Giá trị đơn hàng tối thiểu để áp dụng

    @Column(nullable = false)
    private LocalDateTime validFrom; // Thời gian bắt đầu có hiệu lực

    @Column(nullable = false)
    private LocalDateTime validUntil; // Thời gian hết hạn

    @Column(nullable = false)
    private Integer usageLimit; // Tổng số lần voucher có thể được sử dụng (null cho không giới hạn)

    private Integer usedCount = 0; // Số lần đã được sử dụng

    @Column(nullable = false)
    private boolean active = true; // Trạng thái kích hoạt

    public enum DiscountType {
        PERCENTAGE,    // Giảm theo phần trăm
        FIXED_AMOUNT   // Giảm theo số tiền cố định
    }

    // --- Getters and Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}