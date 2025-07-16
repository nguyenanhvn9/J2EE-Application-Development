package com.hutech.cos141_demo.BaiTH3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hutech.cos141_demo.BaiTH3.dto.UserDto;
import com.hutech.cos141_demo.BaiTH3.model.User;

@Service
public class UserService {
    private static final String USER_API_URL = "https://jsonplaceholder.typicode.com/users";
    private final RestTemplate restTemplate;
    private final List<User> users = new ArrayList<>();
    private boolean loaded = false;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (!loaded || users.isEmpty()) {
            fetchUsersFromApi();
        }
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(int id) {
        return getAllUsers().stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if (getUserById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(int id, User user) {
        Optional<User> existing = getUserById(id);
        if (existing.isPresent()) {
            User u = existing.get();
            u.setName(user.getName());
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void fetchUsersFromApi() {
        UserDto[] userDtos = restTemplate.getForObject(USER_API_URL, UserDto[].class);
        if (userDtos != null) {
            users.clear();
            for (UserDto dto : userDtos) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
            loaded = true;
        }
    }
} 