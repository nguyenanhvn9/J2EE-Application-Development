package com.example.QLS.service;

import com.example.QLS.dto.UserDTO;
import com.example.QLS.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);
        if (response != null) {
            for (UserDTO dto : response) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }

    public User getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public boolean addUser(User user) {
        boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
        if (exists) return false;
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updated) {
        return users.stream().filter(u -> u.getId() == id).findFirst().map(u -> {
            u.setName(updated.getName());
            u.setUsername(updated.getUsername());
            u.setEmail(updated.getEmail());
            u.setPhone(updated.getPhone());
            return u;
        });
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
