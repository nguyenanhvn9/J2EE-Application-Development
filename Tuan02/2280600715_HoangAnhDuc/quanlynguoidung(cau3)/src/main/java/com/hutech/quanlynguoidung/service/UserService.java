package com.hutech.quanlynguoidung.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hutech.quanlynguoidung.dto.UserDTO;
import com.hutech.quanlynguoidung.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);
        if (response != null) {
            for (UserDTO dto : response) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        validateUser(user);
        boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
        if (exists) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        validateUser(updatedUser);
        Optional<User> existing = getUserById(id);
        existing.ifPresent(u -> {
            u.setName(updatedUser.getName());
            u.setUsername(updatedUser.getUsername());
            u.setEmail(updatedUser.getEmail());
            u.setPhone(updatedUser.getPhone());
        });
        return existing;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    public List<User> searchUsers(String keyword) {
        String lower = keyword.toLowerCase();
        return users.stream()
                .filter(u -> u.getName().toLowerCase().contains(lower)
                || u.getUsername().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
}
