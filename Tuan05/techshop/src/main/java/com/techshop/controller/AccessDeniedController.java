package com.techshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class AccessDeniedController {

    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("errorCode", "403");
        model.addAttribute("errorTitle", "Không có quyền truy cập");
        model.addAttribute("errorMessage", "Bạn không có quyền truy cập vào chức năng này. Vui lòng liên hệ quản trị viên để được cấp quyền phù hợp.");
        return "error/403";
    }

    @GetMapping("/404")
    public String notFound(Model model) {
        model.addAttribute("errorCode", "404");
        model.addAttribute("errorTitle", "Không tìm thấy trang");
        model.addAttribute("errorMessage", "Trang bạn đang tìm kiếm không tồn tại hoặc đã được di chuyển.");
        return "error/404";
    }

    @GetMapping("/500")
    public String serverError(Model model) {
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorTitle", "Lỗi máy chủ");
        model.addAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xử lý yêu cầu của bạn.");
        return "error/500";
    }
} 