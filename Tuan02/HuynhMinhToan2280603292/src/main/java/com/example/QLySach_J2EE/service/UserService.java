package com.example.QLySach_J2EE.service;

import com.example.QLySach_J2EE.dto.UserDto;
import com.example.QLySach_J2EE.exception.ResourceNotFoundException;
import com.example.QLySach_J2EE.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    public User getUserById(int id) {
        return getAllUsers().stream().filter(u -> u.getId() == id).findFirst().orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public boolean addUser(User user) {
        validateUser(user);
        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        validateUser(updatedUser);
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(updatedUser.getName());
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name must not be empty");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
    }

    @Cacheable("users")
    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDto[] dtos = restTemplate.getForObject(url, UserDto[].class);
        if (dtos != null) {
            for (UserDto dto : dtos) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }
} 