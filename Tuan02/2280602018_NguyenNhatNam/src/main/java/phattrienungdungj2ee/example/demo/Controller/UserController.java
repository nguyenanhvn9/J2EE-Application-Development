package phattrienungdungj2ee.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import phattrienungdungj2ee.example.demo.model.User;
import phattrienungdungj2ee.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Lấy danh sách người dùng
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Lấy theo ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Thêm mới
    @PostMapping
    public String addUser(@RequestBody User user) {
        boolean added = userService.addUser(user);
        return added ? "Thêm người dùng thành công!" : "ID đã tồn tại.";
    }

    // Cập nhật
    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> result = userService.updateUser(id, updatedUser);
        return result.isPresent() ? "Cập nhật thành công!" : "Không tìm thấy người dùng.";
    }

    // Xóa
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "Xóa thành công!" : "Không tìm thấy người dùng.";
    }
}
