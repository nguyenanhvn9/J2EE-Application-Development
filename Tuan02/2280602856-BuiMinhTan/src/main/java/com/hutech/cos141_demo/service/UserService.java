package com.hutech.cos141_demo.service;

import com.hutech.cos141_demo.dto.UserDto;
import com.hutech.cos141_demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate;

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
        UserDto[] userDtos = restTemplate.getForObject(url, UserDto[].class);

        if (userDtos != null) {
            for (UserDto dto : userDtos) {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                users.add(user);
            }
        }
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(int id, User user) {
        Optional<User> existing = getUserById(id);
        if (existing.isPresent()) {
            User u = existing.get();
            u.setName(user.getName());
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
}
