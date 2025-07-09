package com.garage.garage_backend.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.garage.garage_backend.dtos.UserDTO;
import com.garage.garage_backend.models.User;

import java.util.*;

@Service
@Cacheable("users")
public class UserService {
    private final RestTemplate restTemplate;
    private final List<User> users = new ArrayList<>();

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
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);

        if (response != null) {
            for (UserDTO dto : response) {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                users.add(user);
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (user.getId() == 0 || user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updated) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, new User(id, updated.getName(), updated.getUsername(), updated.getEmail(), updated.getPhone()));
                return Optional.of(users.get(i));
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
