package com.example.j2ee.service;

import com.example.j2ee.dto.UserDTO;
import com.example.j2ee.exception.ResourceNotFoundException;
import com.example.j2ee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate;
    
    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Cacheable("users")
    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return new ArrayList<>(users);
    }
    
    public User getUserById(Integer id) {
        List<User> allUsers = getAllUsers();
        return allUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    public boolean addUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        if (users.stream().anyMatch(existingUser -> existingUser.getId().equals(user.getId()))) {
            throw new IllegalArgumentException("User with this ID already exists");
        }
        
        users.add(user);
        return true;
    }
    
    public Optional<User> updateUser(Integer id, User updatedUser) {
        if (updatedUser.getName() == null || updatedUser.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (updatedUser.getUsername() == null || updatedUser.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (updatedUser.getEmail() == null || updatedUser.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return Optional.of(updatedUser);
            }
        }
        return Optional.empty();
    }
    
    public boolean deleteUser(Integer id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
    
    private void fetchUsersFromApi() {
        try {
            String apiUrl = "https://jsonplaceholder.typicode.com/users";
            UserDTO[] userDTOs = restTemplate.getForObject(apiUrl, UserDTO[].class);
            
            if (userDTOs != null) {
                for (UserDTO userDTO : userDTOs) {
                    User user = convertDTOToUser(userDTO);
                    users.add(user);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching users from API: " + e.getMessage());
            addDefaultUsers();
        }
    }
    
    private User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        return user;
    }
    
    private void addDefaultUsers() {
        users.add(new User(1, "John Doe", "johndoe", "john@example.com", "123-456-7890"));
        users.add(new User(2, "Jane Smith", "janesmith", "jane@example.com", "098-765-4321"));
        users.add(new User(3, "Bob Johnson", "bobjohnson", "bob@example.com", "555-123-4567"));
        users.add(new User(4, "Alice Brown", "alicebrown", "alice@example.com", "444-987-6543"));
        users.add(new User(5, "Charlie Wilson", "charliewilson", "charlie@example.com", "777-555-1234"));
    }
} 