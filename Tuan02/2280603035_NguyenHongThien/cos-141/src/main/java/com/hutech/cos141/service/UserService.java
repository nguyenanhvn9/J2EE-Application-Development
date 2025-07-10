package com.hutech.cos141.service;

import com.hutech.cos141.dto.UserDTO;
import com.hutech.cos141.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
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

    public User getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public boolean addUser(User user) {
        if (user == null || user.getName() == null || user.getName().trim().isEmpty() ||
            user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User name, username, and email must not be null or empty.");
        }
        boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
        if (exists) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        if (updatedUser == null || updatedUser.getName() == null || updatedUser.getName().trim().isEmpty() ||
            updatedUser.getUsername() == null || updatedUser.getUsername().trim().isEmpty() ||
            updatedUser.getEmail() == null || updatedUser.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User name, username, and email must not be null or empty.");
        }
        Optional<User> found = users.stream().filter(u -> u.getId() == id).findFirst();
        found.ifPresent(user -> {
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
        });
        return found;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);
        if (response != null) {
            for (UserDTO dto : Arrays.asList(response)) {
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
}
