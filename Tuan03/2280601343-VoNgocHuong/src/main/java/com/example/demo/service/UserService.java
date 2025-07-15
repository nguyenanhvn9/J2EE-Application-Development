package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";
    private final List<User> users = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return new ArrayList<>(users);
    }

    private void fetchUsersFromApi() {
        try {
            UserDto[] userDtos = restTemplate.getForObject(API_URL, UserDto[].class);
            if (userDtos != null) {
                for (UserDto dto : userDtos) {
                    users.add(convertToUser(dto));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User convertToUser(UserDto dto) {
        return new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
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

    public Optional<User> updateUser(int id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                User user = users.get(i);
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
} 