package com.techshop.controller;

import com.techshop.model.User;
import com.techshop.model.Address;
import com.techshop.model.Order;
import com.techshop.service.UserService;
import com.techshop.service.AddressService;
import com.techshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String profileHome(Authentication authentication, Model model,
            @RequestParam(value = "tab", defaultValue = "info") String tab) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("tab", tab);
        if (tab.equals("addresses")) {
            List<Address> addresses = addressService.getAddressesByUser(user);
            model.addAttribute("addresses", addresses);
        } else if (tab.equals("orders")) {
            List<Order> orders = orderService.findByUserId(user.getId());
            model.addAttribute("orders", orders);
        }
        return "profile";
    }

    @PostMapping("/update-info")
    public String updateInfo(Authentication authentication, @RequestParam String fullName, @RequestParam String phone) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        user.setFullName(fullName);
        user.setPhone(phone);
        userService.register(user); // dùng lại hàm save
        return "redirect:/profile?tab=info";
    }

    // Địa chỉ
    @PostMapping("/address/add")
    public String addAddress(Authentication authentication, @RequestParam String recipientName,
            @RequestParam String phone, @RequestParam String addressLine) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Address address = new Address();
        address.setUser(user);
        address.setRecipientName(recipientName);
        address.setPhone(phone);
        address.setAddressLine(addressLine);
        addressService.saveAddress(address);
        return "redirect:/profile?tab=addresses";
    }

    @PostMapping("/address/edit")
    public String editAddress(@RequestParam Long id, @RequestParam String recipientName, @RequestParam String phone,
            @RequestParam String addressLine) {
        Address address = addressService.getAddress(id);
        if (address != null) {
            address.setRecipientName(recipientName);
            address.setPhone(phone);
            address.setAddressLine(addressLine);
            addressService.saveAddress(address);
        }
        return "redirect:/profile?tab=addresses";
    }

    @PostMapping("/address/delete")
    public String deleteAddress(@RequestParam Long id) {
        addressService.deleteAddress(id);
        return "redirect:/profile?tab=addresses";
    }
}