package com.example.bookservice.service;

import com.example.bookservice.dto.UserDTO;
import com.example.bookservice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final List<User> users = new ArrayList<>();

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
                users.add(new User(dto.id, dto.name, dto.username, dto.email, dto.phone));
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
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
