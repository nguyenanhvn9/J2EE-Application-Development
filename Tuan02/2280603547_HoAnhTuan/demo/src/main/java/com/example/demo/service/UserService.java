package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            logger.info("User list is empty, fetching from API");
            fetchUsersFromApi();
        }
        return users;
    }

    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    public boolean addUser(User user) {
        validateUser(user);
        synchronized (users) {
            if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
                throw new IllegalArgumentException("User with this ID already exists.");
            }
            return users.add(user);
        }
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        validateUser(updatedUser);
        synchronized (users) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getId() == id) {
                    updatedUser.setId(id); // Ensure ID remains unchanged
                    users.set(i, updatedUser);
                    return Optional.of(updatedUser);
                }
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        synchronized (users) {
            return users.removeIf(user -> user.getId() == id);
        }
    }

    private void fetchUsersFromApi() {
        try {
            UserDto[] response = restTemplate.getForObject(API_URL, UserDto[].class);
            if (response != null) {
                for (UserDto dto : response) {
                    User user = new User(
                            dto.getId(),
                            dto.getName(),
                            dto.getUsername(),
                            dto.getEmail(),
                            dto.getPhone()
                    );
                    users.add(user);
                }
                logger.info("Fetched and cached {} users from JSONPlaceholder API", users.size());
            } else {
                logger.error("Failed to fetch users: Empty response");
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error fetching users: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching users: {}", e.getMessage());
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User username cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
    }
}