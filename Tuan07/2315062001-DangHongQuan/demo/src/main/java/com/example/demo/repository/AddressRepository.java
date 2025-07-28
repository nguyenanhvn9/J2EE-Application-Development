package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Tìm tất cả địa chỉ của một người dùng cụ thể
    List<Address> findByUser(User user);

    // Tìm địa chỉ mặc định của một người dùng
    Optional<Address> findByUserAndIsDefaultTrue(User user);
}