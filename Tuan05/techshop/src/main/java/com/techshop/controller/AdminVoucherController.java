package com.techshop.controller;

import com.techshop.model.Voucher;
import com.techshop.service.VoucherService;
import com.techshop.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/vouchers")
public class AdminVoucherController {
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherRepository voucherRepository;

    @GetMapping
    public String listVouchers(Model model) {
        List<Voucher> vouchers = voucherRepository.findAll();
        model.addAttribute("vouchers", vouchers);
        return "admin/vouchers";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "admin/voucher_add";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute("voucher") @Valid Voucher voucher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/voucher_add";
        }
        voucherService.save(voucher);
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if (voucher == null)
            return "redirect:/admin/vouchers";
        model.addAttribute("voucher", voucher);
        return "admin/voucher_edit";
    }

    @PostMapping("/edit/{id}")
    public String editVoucher(@PathVariable Long id, @ModelAttribute("voucher") @Valid Voucher voucher, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/voucher_edit";
        }
        voucher.setId(id);
        voucherService.save(voucher);
        return "redirect:/admin/vouchers";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Long id, Model model) {
        try {
            voucherRepository.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("deleteError", "Không thể xóa mã giảm giá!");
            model.addAttribute("vouchers", voucherRepository.findAll());
            return "admin/vouchers";
        }
        return "redirect:/admin/vouchers";
    }
} 