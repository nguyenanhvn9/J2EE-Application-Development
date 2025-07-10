package com.example.QLySachJ2EE.service;

import com.example.QLySachJ2EE.dto.JsonPlaceholderUserDTO;
import com.example.QLySachJ2EE.exception.ResourceNotFoundException;
import com.example.QLySachJ2EE.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  UserService {
    private List<User> users = new ArrayList<>();
    private boolean isDataLoaded = false;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Cacheable("users")
    public List<User> getAllUsers() {
        if (!isDataLoaded) {
            fetchUsersFromApi();
        }
        return users;
    }
    
    @Cacheable(value = "user", key = "#id")
    public User getUserById(int id) {
        if (!isDataLoaded) {
            fetchUsersFromApi();
        }
        User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return user;
    }
    
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public boolean addUser(User user) {
        // Validation
        validateUser(user);
        
        // Kiểm tra ID đã tồn tại
        if (users.stream().anyMatch(existingUser -> existingUser.getId() == user.getId())) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        
        users.add(user);
        return true;
    }
    
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public Optional<User> updateUser(int id, User updatedUser) {
        // Validation
        validateUser(updatedUser);
        
        Optional<User> existingUser = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            return Optional.of(user);
        }
        
        return Optional.empty();
    }
    
    @CacheEvict(value = {"users", "user"}, allEntries = true)
    public boolean deleteUser(int id) {
        return users.removeIf(u -> u.getId() == id);
    }
    
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
    }
    
    private void fetchUsersFromApi() {
        try {
            String apiUrl = "https://jsonplaceholder.typicode.com/users";
            JsonPlaceholderUserDTO[] response = restTemplate.getForObject(apiUrl, JsonPlaceholderUserDTO[].class);
            
            if (response != null) {
                for (JsonPlaceholderUserDTO dtoUser : response) {
                    User user = new User();
                    user.setId(dtoUser.getId());
                    user.setName(dtoUser.getName());
                    user.setUsername(dtoUser.getUsername());
                    user.setEmail(dtoUser.getEmail());
                    user.setPhone(dtoUser.getPhone());
                    
                    users.add(user);
                }
            }
            isDataLoaded = true;
        } catch (Exception e) {
            System.err.println("Error fetching users from API: " + e.getMessage());
            // Nếu API không hoạt động, thêm một số dữ liệu mẫu
            addSampleUsers();
            isDataLoaded = true;
        }
    }
    
    private void addSampleUsers() {
        users.add(new User(1, "John Doe", "johndoe", "john@example.com", "123-456-7890"));
        users.add(new User(2, "Jane Smith", "janesmith", "jane@example.com", "098-765-4321"));
        users.add(new User(3, "Bob Johnson", "bobjohnson", "bob@example.com", "555-123-4567"));
        users.add(new User(4, "Alice Brown", "alicebrown", "alice@example.com", "777-888-9999"));
        users.add(new User(5, "Charlie Wilson", "charliewilson", "charlie@example.com", "111-222-3333"));
    }
} 