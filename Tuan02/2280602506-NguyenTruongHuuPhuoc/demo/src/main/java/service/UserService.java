package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final List<User> users = new ArrayList<>();

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    // Gọi API để fetch dữ liệu
    @Cacheable("users") // ✅ cache kết quả khi gọi lần đầu
    public List<User> fetchUsersFromApi() {
        String apiUrl = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] userDTOs = restTemplate.getForObject(apiUrl, UserDTO[].class);
        users.clear();
        users.addAll(
            Arrays.stream(userDTOs)
                .map(dto -> new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()))
                .collect(Collectors.toList())
        );
        return users;
    }

    // Lấy user theo ID
    public User getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm user
    public boolean addUser(User user) {
        if (getUserById(user.getId()) != null) {
            throw new IllegalArgumentException("User with ID already exists.");
        }
        if (user.getName() == null || user.getName().isEmpty() ||
            user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("User data is invalid.");
        }
        users.add(user);
        return true;
    }

    // Cập nhật user
    public boolean updateUser(int id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }

    // Xóa user theo ID
    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
