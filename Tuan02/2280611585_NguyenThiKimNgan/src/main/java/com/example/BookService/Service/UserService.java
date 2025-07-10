package com.example.BookService.Service;

import com.example.BookService.DTO.UserDTO;
import com.example.BookService.DTO.UserResponse;
import com.example.BookService.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final List<User> users = new ArrayList<>();

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserDTO> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserResponse[] response = restTemplate.getForObject(url, UserResponse[].class);

        if (response != null) {
            for (UserResponse dto : response) {
                User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
                users.add(user);
            }
        }
    }

    public Optional<UserDTO> getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .map(this::convertToDTO)
                .findFirst();
    }

    public boolean addUser(UserDTO userDTO) {
        boolean exists = users.stream().anyMatch(u -> u.getId() == userDTO.getId());
        if (exists) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }

        users.add(convertToEntity(userDTO));
        return true;
    }

    public Optional<UserDTO> updateUser(int id, UserDTO updatedUser) {
        for (User u : users) {
            if (u.getId() == id) {
                u.setName(updatedUser.getName());
                u.setUsername(updatedUser.getUsername());
                u.setEmail(updatedUser.getEmail());
                u.setPhone(updatedUser.getPhone());

                return Optional.of(convertToDTO(u));
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPhone());
    }

    private User convertToEntity(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone());
    }
}
