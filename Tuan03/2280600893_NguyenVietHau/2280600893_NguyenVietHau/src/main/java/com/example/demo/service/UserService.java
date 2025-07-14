package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate(); // Inject thủ công

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addUser(User user) {
        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            return false;
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(int id, User updatedUser) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(updatedUser.getName());
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);

        if (response != null) {
            for (UserDTO dto : response) {
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
