package com.example.bookservice.service;

import com.example.bookservice.dto.UserDTO;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.model.User;
import com.example.bookservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String JSONPLACEHOLDER_API_URL = "https://jsonplaceholder.typicode.com/users";

    // Lấy tất cả người dùng với lazy loading
    public List<UserDTO> getAllUsers() {
        // Lazy loading: nếu không có user nào, fetch từ API
        if (userRepository.count() == 0) {
            fetchUsersFromApi();
        }
        
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch dữ liệu từ JSONPlaceholder API
    private void fetchUsersFromApi() {
        try {
            UserDTO[] userArray = restTemplate.getForObject(JSONPLACEHOLDER_API_URL, UserDTO[].class);
            
            if (userArray != null) {
                List<User> users = List.of(userArray).stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList());
                
                userRepository.saveAll(users);
            }
        } catch (Exception e) {
            System.err.println("Error fetching users from JSONPlaceholder API: " + e.getMessage());
        }
    }

    // Lấy người dùng theo ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    // Tạo người dùng mới
    public UserDTO createUser(UserDTO userDTO) {
        validateUser(userDTO);
        
        // Kiểm tra trùng username và email
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        }
        
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }
        
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Cập nhật người dùng
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        validateUser(userDTO);
        
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Kiểm tra trùng username và email (trừ chính user đang cập nhật)
                    if (!existingUser.getUsername().equals(userDTO.getUsername()) && 
                        userRepository.existsByUsername(userDTO.getUsername())) {
                        throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
                    }
                    
                    if (!existingUser.getEmail().equals(userDTO.getEmail()) && 
                        userRepository.existsByEmail(userDTO.getEmail())) {
                        throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
                    }
                    
                    existingUser.setFullName(userDTO.getName());
                    existingUser.setUsername(userDTO.getUsername());
                    existingUser.setEmail(userDTO.getEmail());
                    
                    User updatedUser = userRepository.save(existingUser);
                    return convertToDTO(updatedUser);
                });
    }

    // Xóa người dùng
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Đếm số lượng người dùng
    public long countUsers() {
        return userRepository.count();
    }

    // Tìm người dùng theo email
    public Optional<UserDTO> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::convertToDTO);
    }

    // Validation cho User
    private void validateUser(UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        // Validate email format
        if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Chuyển đổi Entity sang DTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }

    // Chuyển đổi DTO sang Entity
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setFullName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }
}
