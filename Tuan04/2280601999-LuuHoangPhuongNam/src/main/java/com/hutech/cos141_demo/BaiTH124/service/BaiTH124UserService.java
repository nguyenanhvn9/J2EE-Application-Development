package com.hutech.cos141_demo.BaiTH124.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hutech.cos141_demo.BaiTH124.model.User;

@Service
public class BaiTH124UserService {
    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void updateUser(User updatedUser) {
        users.stream()
            .filter(user -> user.getId().equals(updatedUser.getId()))
            .findFirst()
            .ifPresent(user -> {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
            });
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
} 