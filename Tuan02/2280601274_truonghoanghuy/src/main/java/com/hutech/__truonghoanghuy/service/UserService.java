package com.hutech.__truonghoanghuy.service;

import com.hutech.__truonghoanghuy.dto.UserDTO;
import com.hutech.__truonghoanghuy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
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
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] userDTOs = restTemplate.getForObject(url, UserDTO[].class);
        if (userDTOs != null) {
            for (UserDTO dto : userDTOs) {
                users.add(new User(dto.id, dto.name, dto.username, dto.email, dto.phone));
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty() ||
            user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User name, username, and email must not be empty.");
        }
        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User user) {
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
} 