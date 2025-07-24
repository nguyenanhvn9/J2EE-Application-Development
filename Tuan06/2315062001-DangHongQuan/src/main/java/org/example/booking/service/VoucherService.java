package org.example.booking.service;

import org.example.booking.Response.VoucherRepository;
import org.example.booking.model.User;
import org.example.booking.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> findById(Long id) {
        return voucherRepository.findById(id);
    }

    public void deleteById(Long id) {
        voucherRepository.deleteById(id);
    }
    public Voucher findByCode(String code) {
        return voucherRepository.findByCode(code).orElse(null);
    }

    public boolean isValid(Voucher voucher, User user) {
        LocalDateTime now = LocalDateTime.now();

        // Kiểm tra ngày hết hạn
        if (voucher.getEndDate().isBefore(now)) {
            return false;
        }

        // Nếu cần kiểm tra user đã dùng voucher chưa, có thể làm thêm

        return true;
    }

}
