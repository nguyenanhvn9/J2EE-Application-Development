package com.example.QLySach_J2EE.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        model.addAttribute("status", status != null ? status.toString() : "Unknown");
        model.addAttribute("error", message != null ? message.toString() : "Unknown Error");
        model.addAttribute("path", path != null ? path.toString() : "/");
        model.addAttribute("message", "Đã xảy ra lỗi trong ứng dụng. Vui lòng thử lại sau.");
        
        return "error";
    }
} 