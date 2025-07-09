package com.hutech.__vongochuong.service;

import com.hutech.__vongochuong.model.User;
import com.hutech.__vongochuong.dto.UserDto;
import com.hutech.__vongochuong.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (users.isEmpty()) fetchUsersFromApi();
        return users;
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDto[] dtos = restTemplate.getForObject(url, UserDto[].class);
        if (dtos != null) {
            for (UserDto dto : dtos) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }

    public User getUserById(int id) {
        if (users.isEmpty()) fetchUsersFromApi();
        return users.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public boolean addUser(User user) {
        validateUser(user);
        for (User u : users) {
            if (u.getId() == user.getId()) {
                throw new IllegalArgumentException("User with this ID already exists.");
            }
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User user) {
        validateUser(user);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty() ||
            user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Name, username, and email must not be empty");
        }
    }
} 