package com.example.studentmanagement.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleDataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // Bỏ qua việc tạo dữ liệu mẫu vì đã có DB sẵn
        System.out.println("=== ỨNG DỤNG ĐÃ KHỞI ĐỘNG THÀNH CÔNG ===");
        System.out.println("Truy cập: http://localhost:8080");
    }
}
