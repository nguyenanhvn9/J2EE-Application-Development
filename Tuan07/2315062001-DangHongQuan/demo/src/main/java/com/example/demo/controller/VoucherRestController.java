package com.example.demo.controller;

import com.example.demo.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/voucher")
public class VoucherRestController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/validate")
    public ResponseEntity<?> validateVoucher(@RequestParam String code, @RequestParam BigDecimal orderTotal) {
        try {
            BigDecimal discountAmount = voucherService.validateAndCalculateDiscount(code, orderTotal);
            Map<String, Object> response = new HashMap<>();
            response.put("discountAmount", discountAmount);
            response.put("message", "Mã giảm giá hợp lệ.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Đã xảy ra lỗi nội bộ.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}