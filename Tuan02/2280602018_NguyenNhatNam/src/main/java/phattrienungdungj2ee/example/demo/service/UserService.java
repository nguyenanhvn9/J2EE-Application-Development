package phattrienungdungj2ee.example.demo.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import phattrienungdungj2ee.example.demo.dto.UserDTO;
import phattrienungdungj2ee.example.demo.model.User;

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

    public User getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public boolean addUser(User user) {
        boolean exists = users.stream().anyMatch(u -> u.getId() == user.getId());
        if (exists) return false;
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        return users.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .map(user -> {
                user.setName(updatedUser.getName());
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
                return user;
            });
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private void fetchUsersFromApi() {
        String apiUrl = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] dtos = restTemplate.getForObject(apiUrl, UserDTO[].class);

        if (dtos != null) {
            Arrays.stream(dtos).forEach(dto -> {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                users.add(user);
            });
        }
    }
}
