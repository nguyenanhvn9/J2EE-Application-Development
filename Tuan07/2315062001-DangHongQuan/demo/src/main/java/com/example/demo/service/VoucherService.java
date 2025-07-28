// src/main/java/com/example/demo/service/VoucherService.java
package com.example.demo.service;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    public Optional<Voucher> findByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    /**
     * Kiểm tra tính hợp lệ của voucher và tính toán số tiền giảm giá.
     *
     * @param voucherCode Mã voucher người dùng nhập.
     * @param orderTotal Tổng tiền hiện tại của đơn hàng (trước khi giảm giá).
     * @return Số tiền giảm giá được áp dụng (BigDecimal.ZERO nếu không hợp lệ).
     * @throws IllegalArgumentException Nếu voucher không hợp lệ với lý do cụ thể.
     */
    public BigDecimal validateAndCalculateDiscount(String voucherCode, BigDecimal orderTotal) {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            return BigDecimal.ZERO; // Không có mã, không giảm giá
        }

        Optional<Voucher> voucherOptional = voucherRepository.findByCode(voucherCode);

        if (voucherOptional.isEmpty()) {
            throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
        }

        Voucher voucher = voucherOptional.get();
        LocalDateTime now = LocalDateTime.now();

        if (!voucher.isActive()) {
            throw new IllegalArgumentException("Mã giảm giá không hoạt động.");
        }
        if (now.isBefore(voucher.getValidFrom())) {
            throw new IllegalArgumentException("Mã giảm giá chưa đến thời gian áp dụng.");
        }
        if (now.isAfter(voucher.getValidUntil())) {
            throw new IllegalArgumentException("Mã giảm giá đã hết hạn.");
        }
        if (voucher.getUsageLimit() != null && voucher.getUsedCount() >= voucher.getUsageLimit()) {
            throw new IllegalArgumentException("Mã giảm giá đã hết lượt sử dụng.");
        }
        if (voucher.getMinimumOrderAmount() != null && orderTotal.compareTo(voucher.getMinimumOrderAmount()) < 0) {
            throw new IllegalArgumentException("Đơn hàng chưa đạt giá trị tối thiểu " + voucher.getMinimumOrderAmount().toPlainString() + " VND để áp dụng mã này.");
        }

        // Tính toán số tiền giảm giá
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (voucher.getDiscountType() == Voucher.DiscountType.PERCENTAGE) {
            discountAmount = orderTotal.multiply(voucher.getDiscountValue().divide(BigDecimal.valueOf(100)));
        } else if (voucher.getDiscountType() == Voucher.DiscountType.FIXED_AMOUNT) {
            discountAmount = voucher.getDiscountValue();
        }

        // Đảm bảo số tiền giảm giá không lớn hơn tổng đơn hàng
        if (discountAmount.compareTo(orderTotal) > 0) {
            discountAmount = orderTotal;
        }

        return discountAmount;
    }

    @Transactional
    public void returnVoucherUsage(String voucherCode) {
        Voucher voucher = voucherRepository.findByCode(voucherCode)
                .orElse(null);

        if (voucher != null) {
            // Sử dụng getUsedCount() và setUsedCount()
            if (voucher.getUsedCount() > 0) {
                voucher.setUsedCount(voucher.getUsedCount() - 1); // Đã sửa từ getTimesUsed
                voucherRepository.save(voucher);
            }
        } else {
            System.err.println("Cảnh báo: Không tìm thấy voucher với mã " + voucherCode + " để hoàn trả lượt sử dụng.");
        }
    }

    /**
     * Tăng số lượt sử dụng của voucher sau khi đơn hàng được đặt thành công.
     * Cần được gọi trong OrderService khi tạo đơn hàng.
     * @param voucherCode
     */
    @Transactional
    public void incrementVoucherUsage(String voucherCode) {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            return;
        }
        Optional<Voucher> voucherOptional = voucherRepository.findByCode(voucherCode);
        if (voucherOptional.isPresent()) {
            Voucher voucher = voucherOptional.get();
            voucher.setUsedCount(voucher.getUsedCount() + 1);
            voucherRepository.save(voucher);
        }
    }

    // Các phương thức quản lý voucher (thêm, sửa, xóa) có thể thêm vào đây cho Admin
    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucherById(Long id) {
        return voucherRepository.findById(id);
    }

    public void deleteVoucher(Long id) {
        voucherRepository.deleteById(id);
    }
}