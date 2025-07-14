package com.hutech.cos141_demo.BaiTH03;

import com.hutech.cos141_demo.BaiTH03.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate;

    @Autowired
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
        UserDTO[] userDTOs = restTemplate.getForObject(url, UserDTO[].class);
        if (userDTOs != null) {
            for (UserDTO dto : userDTOs) {
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
        if (user == null) throw new IllegalArgumentException("User cannot be null.");
        if (users.stream().anyMatch(u -> u.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                user.setId(id); // Đảm bảo id không đổi
                users.set(i, user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
} 