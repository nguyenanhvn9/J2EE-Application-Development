package com.example.BaitapJ2EE.QuanLyNguoiDung.service;

import com.example.BaitapJ2EE.QuanLyNguoiDung.model.User;
import com.example.BaitapJ2EE.QuanLyNguoiDung.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    private void fetchUsersFromApi() {
        UserDTO[] dtos = restTemplate.getForObject(API_URL, UserDTO[].class);
        if (dtos != null) {
            for (UserDTO dto : dtos) {
                users.add(new User(
                    dto.getId(),
                    dto.getName(),
                    dto.getUsername(),
                    dto.getEmail(),
                    dto.getPhone()
                ));
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty() || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User name and username must not be null or empty.");
        }
        for (User u : users) {
            if (u.getId() == user.getId()) {
                throw new IllegalArgumentException("User with this ID already exists.");
            }
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty() || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User name and username must not be null or empty.");
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user.setId(id);
                users.set(i, user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
} 