package com.hutech.cos141_demo.service;

import com.hutech.cos141_demo.dto.UserDTO;
import com.hutech.cos141_demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserService {
    
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final List<User> users = new CopyOnWriteArrayList<>();
    
    public UserService(RestTemplate restTemplate, @Value("${api.users.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }
    
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return new ArrayList<>(users);
    }
    
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public User addUser(User user) {
        // Tìm ID lớn nhất hiện tại
        int maxId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
        // Set ID mới
        user.setId(maxId + 1);
        users.add(user);
        return user;
    }
    
    public void updateUser(int id, User userDetails) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                userDetails.setId(id); // Đảm bảo giữ nguyên ID
                users.set(i, userDetails);
                return;
            }
        }
    }
    
    public boolean deleteUser(int id) {
        return users.removeIf(user -> user.getId() == id);
    }
    
    private void fetchUsersFromApi() {
        UserDTO[] userDTOs = restTemplate.getForObject(apiUrl, UserDTO[].class);
        if (userDTOs != null) {
            Arrays.stream(userDTOs)
                    .map(User::fromDTO)
                    .forEach(users::add);
        }
    }
}
