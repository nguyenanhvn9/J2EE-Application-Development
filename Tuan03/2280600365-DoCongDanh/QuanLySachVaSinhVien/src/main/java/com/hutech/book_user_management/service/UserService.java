package com.hutech.book_user_management.service;

import com.example.book_user_management.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<UserDTO> getAllUsers() {
        UserDTO[] users = restTemplate.getForObject(BASE_URL, UserDTO[].class);
        return Arrays.asList(users);
    }

    public UserDTO getUserById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, UserDTO.class);
    }

    public void createUser(UserDTO user) {
        restTemplate.postForObject(BASE_URL, user, UserDTO.class);
    }

    public void updateUser(UserDTO user) {
        restTemplate.put(BASE_URL + "/" + user.getId(), user);
    }

    public void deleteUser(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
