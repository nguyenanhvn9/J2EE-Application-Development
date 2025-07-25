package com.techshop.service;

import com.techshop.model.Voucher;
import com.techshop.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher findByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    public boolean isValid(Voucher voucher) {
        if (voucher == null)
            return false;
        if (!voucher.isActive())
            return false;
        if (voucher.getExpiryDate() != null && voucher.getExpiryDate().isBefore(LocalDateTime.now()))
            return false;
        if (voucher.getUsageLimit() > 0 && voucher.getUsedCount() >= voucher.getUsageLimit())
            return false;
        return true;
    }

    public Voucher save(Voucher voucher) {
        return voucherRepository.save(voucher);
    }
}