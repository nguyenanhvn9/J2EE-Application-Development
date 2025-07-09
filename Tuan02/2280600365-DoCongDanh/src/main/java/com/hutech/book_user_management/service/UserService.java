package com.hutech.book_user_management.service;

import com.hutech.book_user_management.dto.UserDTO;
import com.hutech.book_user_management.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        UserDTO[] response = restTemplate.getForObject(url, UserDTO[].class);

        if (response != null) {
            for (UserDTO dto : response) {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                users.add(user);
            }
        }
    }

    public User getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public boolean addUser(User user) {
        if (getUserById(user.getId()) != null) return false;
        users.add(user);
        return true;
    }

    public boolean updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
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
