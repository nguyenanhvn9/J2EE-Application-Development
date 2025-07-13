package com.tuan2.service;

import com.tuan2.dto.UserDto;
import com.tuan2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final List<User> userList = new ArrayList<>();
    private final String EXTERNAL_API = "https://jsonplaceholder.typicode.com/users";

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (userList.isEmpty()) {
            fetchUsersFromApi();
        }
        return userList;
    }

    private void fetchUsersFromApi() {
        UserDto[] dtos = restTemplate.getForObject(EXTERNAL_API, UserDto[].class);
        if (dtos != null) {
            for (UserDto dto : dtos) {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                userList.add(user);
            }
        }
    }

    public User getUserById(int id) {
        return userList.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User addUser(User user) {
        user.setId(generateNewId());
        userList.add(user);
        return user;
    }

    public User updateUser(int id, User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                updatedUser.setId(id);
                userList.set(i, updatedUser);
                return updatedUser;
            }
        }
        return null;
    }

    public boolean deleteUser(int id) {
        return userList.removeIf(user -> user.getId() == id);
    }

    private int generateNewId() {
        return userList.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0) + 1;
    }
}
