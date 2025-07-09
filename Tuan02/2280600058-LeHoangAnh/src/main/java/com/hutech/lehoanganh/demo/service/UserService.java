package com.hutech.lehoanganh.demo.service;

import com.hutech.lehoanganh.demo.dto.UserDto;
import com.hutech.lehoanganh.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final String API_URL = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    private void fetchUsersFromApi() {
        UserDto[] dtos = restTemplate.getForObject(API_URL, UserDto[].class);
        if (dtos != null) {
            for (UserDto dto : dtos) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            return false;
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(int id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
} 