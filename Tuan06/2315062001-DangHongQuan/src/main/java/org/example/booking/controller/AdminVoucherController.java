package org.example.booking.controller;

import org.example.booking.model.Voucher;
import org.example.booking.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/vouchers")
public class AdminVoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public String listAndForm(@RequestParam(required = false) Long editId, Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        Voucher formVoucher = (editId != null) ?
                voucherService.findById(editId).orElse(new Voucher()) :
                new Voucher();

        model.addAttribute("vouchers", vouchers);
        model.addAttribute("voucher", formVoucher);
        return "admin/voucher/list";  // dùng 1 view duy nhất
    }

    @PostMapping("/save")
    public String saveVoucher(@ModelAttribute Voucher voucher) {
        voucherService.save(voucher);
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Long id) {
        voucherService.deleteById(id);
        return "redirect:/admin/vouchers";
    }
}
