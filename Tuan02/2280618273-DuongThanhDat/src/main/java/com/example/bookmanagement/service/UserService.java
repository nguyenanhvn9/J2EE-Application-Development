package com.example.bookmanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final Map<Long, User> userStorage = new ConcurrentHashMap<>();
    private static final String JSONPLACEHOLDER_API_URL = "https://jsonplaceholder.typicode.com/users";
    private boolean isDataLoaded = false;

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    // Lazy Loading: Load data only when first accessed
    private void loadUsersIfNeeded() {
        if (!isDataLoaded) {
            synchronized (this) {
                if (!isDataLoaded) {
                    fetchUsersFromApi();
                    isDataLoaded = true;
                }
            }
        }
    }

    // Fetch users from JSONPlaceholder API
    private void fetchUsersFromApi() {
        try {
            UserDto[] userDtos = restTemplate.getForObject(JSONPLACEHOLDER_API_URL, UserDto[].class);
            if (userDtos != null) {
                for (UserDto userDto : userDtos) {
                    User user = convertToUser(userDto);
                    userStorage.put(user.getId(), user);
                }
                System.out.println("Loaded " + userStorage.size() + " users from JSONPlaceholder API");
            }
        } catch (Exception e) {
            System.err.println("Error loading users from API: " + e.getMessage());
        }
    }

    // Convert UserDto to User
    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setWebsite(userDto.getWebsite());

        // Convert address to string
        if (userDto.getAddress() != null) {
            String address = String.format("%s, %s, %s %s",
                    userDto.getAddress().getStreet(),
                    userDto.getAddress().getSuite(),
                    userDto.getAddress().getCity(),
                    userDto.getAddress().getZipcode());
            user.setAddress(address);
        }

        // Convert company to string
        if (userDto.getCompany() != null) {
            user.setCompany(userDto.getCompany().getName());
        }

        return user;
    }

    // Get all users
    public List<User> getAllUsers() {
        loadUsersIfNeeded();
        return new ArrayList<>(userStorage.values());
    }

    // Get user by ID
    public User getUserById(Long id) {
        loadUsersIfNeeded();
        User user = userStorage.get(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return user;
    }

    // Add a new user
    public User addUser(User user) {
        validateUser(user);

        if (user.getId() == null) {
            // Generate new ID
            long newId = userStorage.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
            user.setId(newId);
        }

        // Check if user ID already exists
        if (userStorage.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " already exists");
        }

        userStorage.put(user.getId(), user);
        return user;
    }

    // Update user
    public User updateUser(Long id, User updatedUser) {
        loadUsersIfNeeded();
        User existingUser = userStorage.get(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        validateUser(updatedUser);
        updatedUser.setId(id);
        userStorage.put(id, updatedUser);
        return updatedUser;
    }

    // Delete user
    public void deleteUser(Long id) {
        loadUsersIfNeeded();
        User user = userStorage.remove(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
    }

    // Validate user data
    private void validateUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Simple email validation
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Search users by name
    public List<User> searchByName(String name) {
        loadUsersIfNeeded();
        return userStorage.values().stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    // Search users by username
    public List<User> searchByUsername(String username) {
        loadUsersIfNeeded();
        return userStorage.values().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    // Get total count of users
    public long getTotalUsersCount() {
        loadUsersIfNeeded();
        return userStorage.size();
    }
}
