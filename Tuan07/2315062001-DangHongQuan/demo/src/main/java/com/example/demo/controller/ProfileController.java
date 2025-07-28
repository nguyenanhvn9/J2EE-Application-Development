package com.example.demo.controller;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.UserProfileDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.service.AddressService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    // Lấy thông tin người dùng hiện tại
    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        String username = authentication.getName();
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found!"));
    }

    // --- Tab Thông tin cá nhân ---
    // Phương thức chính để hiển thị trang hồ sơ
    @GetMapping // Vẫn giữ nguyên mapping này
    public String viewProfile(Model model) { // Loại bỏ @RequestParam "tab"
        User currentUser = getCurrentAuthenticatedUser();

        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", null);
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", null);
        }

        // --- Dữ liệu cho phần Thông tin cá nhân ---
        UserProfileDto userProfileDto = (UserProfileDto) model.getAttribute("userProfileDto");
        if (userProfileDto == null) {
            userProfileDto = new UserProfileDto();
            userProfileDto.setFullName(currentUser.getFullName());
            userProfileDto.setPhone(currentUser.getPhone());
        }
        model.addAttribute("userProfileDto", userProfileDto);

        // --- Dữ liệu cho phần Sổ địa chỉ ---
        List<Address> addresses = addressService.getAddressesByUser(currentUser.getId());
        List<AddressDto> addressDtos = addresses.stream()
                .map(address -> {
                    AddressDto dto = new AddressDto();
                    BeanUtils.copyProperties(address, dto);
                    dto.setDefault(address.isDefault());
                    return dto;
                })
                .collect(Collectors.toList());
        model.addAttribute("addresses", addressDtos);

        if (!model.containsAttribute("newAddress")) {
            model.addAttribute("newAddress", new AddressDto());
        }
        // addressToEdit sẽ được thêm vào model bởi phương thức editAddressForm nếu có yêu cầu chỉnh sửa
        // hoặc để trống nếu không có
        if (!model.containsAttribute("addressToEdit")) {
            model.addAttribute("addressToEdit", new AddressDto());
        }


        // --- Dữ liệu cho phần Lịch sử đơn hàng ---
        List<Order> orders = orderService.getOrdersByUser(currentUser.getId());
        model.addAttribute("orders", orders);

        // Không cần activeTab nữa
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/update-info")
    public String updateProfileInfo(@Valid @ModelAttribute("userProfileDto") UserProfileDto userProfileDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileDto", bindingResult);
            redirectAttributes.addFlashAttribute("userProfileDto", userProfileDto);
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin cá nhân.");
            // Không cần '?tab=info' nữa
            return "redirect:/profile";
        }

        try {
            User updatedUserFromDb = userService.updateUserProfile(currentUser.getId(), userProfileDto);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                    updatedUserFromDb,
                    authentication.getCredentials(),
                    updatedUserFromDb.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin cá nhân thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        // Không cần '?tab=info' nữa
        return "redirect:/profile";
    }

    // --- NEW METHOD TO SHOW ADD ADDRESS FORM ---
    @GetMapping("/addresses/add")
    public String showAddAddressForm(Model model) {
        if (!model.containsAttribute("newAddress")) {
            model.addAttribute("newAddress", new AddressDto());
        }
        // This is to display success/error messages from redirect
        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", null);
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", null);
        }
        return "add-address"; // Return the name of the new HTML file
    }

    // --- Original addAddress POST method (modified redirect) ---
    // Trong ProfileController.java
    @PostMapping("/addresses/add")
    public String addAddress(@Valid @ModelAttribute("newAddress") AddressDto addressDto,
                             BindingResult bindingResult,
                             // Thêm @RequestParam Boolean isDefaultChecked để nhận giá trị checkbox
                             @RequestParam(value = "isDefault", required = false) Boolean isDefaultChecked,
                             RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentAuthenticatedUser();

        // Nếu có lỗi validation khác, vẫn trả về form
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm địa chỉ. Vui lòng kiểm tra lại thông tin.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newAddress", bindingResult);
            redirectAttributes.addFlashAttribute("newAddress", addressDto);
            return "redirect:/profile/addresses/add";
        }

        try {
            Address newAddressEntity = new Address();
            BeanUtils.copyProperties(addressDto, newAddressEntity);

            // Xử lý giá trị isDefault thủ công từ checkbox
            // Nếu isDefaultChecked là null (checkbox không được chọn), đặt là false
            // Ngược lại (checkbox được chọn), đặt là true
            newAddressEntity.setDefault(isDefaultChecked != null && isDefaultChecked);

            newAddressEntity.setUser(currentUser); // User will be set in service

            addressService.addAddress(currentUser.getId(), newAddressEntity);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm địa chỉ thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/profile";
    }

    // Phương thức GET (đã có và được gọi khi bạn click Sửa)
    @GetMapping("/addresses/edit/{id}")
    public String editAddressForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentAuthenticatedUser();
        Optional<Address> addressOptional = addressService.getAddressById(id);

        if (addressOptional.isEmpty() || !addressOptional.get().getUser().getId().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không tồn tại hoặc bạn không có quyền truy cập.");
            return "redirect:/profile";
        }

        Address address = addressOptional.get();
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(address, addressDto);
        addressDto.setDefault(address.isDefault()); // Đảm bảo isDefault được copy đúng (đây là getter trong entity)

        model.addAttribute("addressToEdit", addressDto); // Đặt DTO vào model với tên này
        model.addAttribute("user", currentUser);

        return "edit-address"; // Tên file HTML của bạn
    }


    // PHƯƠNG THỨC POST MỚI ĐỂ XỬ LÝ CHỈNH SỬA ĐỊA CHỈ
    @PostMapping("/addresses/edit")
    public String updateAddress(@Valid @ModelAttribute("addressToEdit") AddressDto addressDto,
                                BindingResult bindingResult,
                                @RequestParam(value = "isDefault", required = false) Boolean isDefaultChecked, // Nhận giá trị checkbox
                                RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentAuthenticatedUser();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật địa chỉ. Vui lòng kiểm tra lại thông tin.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addressToEdit", bindingResult);
            redirectAttributes.addFlashAttribute("addressToEdit", addressDto);
            return "redirect:/profile/addresses/edit/" + addressDto.getId(); // Redirect về trang sửa với ID
        }

        try {
            // Lấy địa chỉ hiện có từ DB để cập nhật
            Optional<Address> existingAddressOptional = addressService.getAddressById(addressDto.getId());

            if (existingAddressOptional.isEmpty() || !existingAddressOptional.get().getUser().getId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không tồn tại hoặc bạn không có quyền cập nhật.");
                return "redirect:/profile";
            }

            Address existingAddress = existingAddressOptional.get();

            // Copy các thuộc tính từ DTO sang entity, giữ lại các thuộc tính không thay đổi (như user, created_at)
            BeanUtils.copyProperties(addressDto, existingAddress, "id", "user", "createdAt"); // Loại trừ id, user, createdAt khi copy

            // Xử lý giá trị isDefault thủ công
            existingAddress.setDefault(isDefaultChecked != null && isDefaultChecked);

            existingAddress.setUpdatedAt(LocalDateTime.now()); // Cập nhật thời gian chỉnh sửa

            addressService.updateAddress(currentUser.getId(), addressDto.getId(), addressDto, isDefaultChecked); // <<< SỬA CÁCH GỌI
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật địa chỉ thành công!");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/profile"; // Chuyển về trang hồ sơ sau khi cập nhật
    }

    @PostMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentAuthenticatedUser();
        try {
            addressService.deleteAddress(currentUser.getId(), id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa địa chỉ thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/profile";
    }
}