package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    private List<User> users = null;

    public List<User> getAllUsers() {
        if (users == null) {
            fetchUsersFromApi();
        }
        return users;
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] dtos = restTemplate.getForObject(url, UserDTO[].class);
        users = new ArrayList<>();
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
        return getAllUsers().stream().filter(u -> u.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (getAllUsers().stream().anyMatch(u -> u.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User user) {
        for (int i = 0; i < getAllUsers().size(); i++) {
            if (users.get(i).getId() == id) {
                user.setId(id);
                users.set(i, user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        Iterator<User> iterator = getAllUsers().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
} 