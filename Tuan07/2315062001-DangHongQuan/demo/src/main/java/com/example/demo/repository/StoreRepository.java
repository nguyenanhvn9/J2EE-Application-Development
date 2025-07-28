package com.example.demo.repository;

// package com.example.demo.repository;

import com.example.demo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // Có thể thêm các phương thức tìm kiếm tùy chỉnh nếu cần, ví dụ: findByNameContainingIgnoreCase
}