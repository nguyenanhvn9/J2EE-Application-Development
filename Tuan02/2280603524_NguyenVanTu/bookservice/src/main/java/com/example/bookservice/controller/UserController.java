package com.example.bookservice.controller;

import com.example.bookservice.dto.UserDTO;
import com.example.bookservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /api/users - Lấy tất cả người dùng
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id} - Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // POST /api/users - Tạo người dùng mới
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // PUT /api/users/{id} - Cập nhật người dùng
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, 
                                            @Valid @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUser = userService.updateUser(id, userDTO);
        return updatedUser.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/users/{id} - Xóa người dùng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() 
                       : ResponseEntity.notFound().build();
    }

    // GET /api/users/count - Đếm số lượng người dùng
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getUserCount() {
        long count = userService.countUsers();
        return ResponseEntity.ok(Map.of("count", count));
    }

    // GET /api/users/check-email - Kiểm tra email có tồn tại
    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Object>> checkEmailExists(@RequestParam String email) {
        Optional<UserDTO> existingUser = userService.findByEmail(email);
        Map<String, Object> response = Map.of(
            "exists", existingUser.isPresent(),
            "userId", existingUser.map(u -> u.getId()).orElse(null)
        );
        return ResponseEntity.ok(response);
    }
}
