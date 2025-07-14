package com.lehoang.demo_lehoang.service;

import com.lehoang.demo_lehoang.model.User;
import com.lehoang.demo_lehoang.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return users;
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] dtos = restTemplate.getForObject(url, UserDTO[].class);
        if (dtos != null) {
            for (UserDTO dto : dtos) {
                User user = new User();
                user.setId(dto.getId());
                user.setName(dto.getName());
                user.setUsername(dto.getUsername());
                user.setEmail(dto.getEmail());
                user.setPhone(dto.getPhone());
                users.add(user);
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

    public boolean updateUser(int id, User updatedUser) {
        Optional<User> opt = getUserById(id);
        if (opt.isPresent()) {
            User user = opt.get();
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
} 