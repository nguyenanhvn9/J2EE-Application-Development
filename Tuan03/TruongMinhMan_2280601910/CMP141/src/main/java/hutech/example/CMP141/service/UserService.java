package hutech.example.CMP141.service;

import hutech.example.CMP141.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchRandomUsersFromApi();
        }
        return users.size() > 5 ? users.subList(0, 5) : users;
    }

    private void fetchRandomUsersFromApi() {
        users.clear();
        String url = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        UserApiDTO[] response = restTemplate.getForObject(url, UserApiDTO[].class);
        if (response != null && response.length > 0) {
            List<UserApiDTO> all = new ArrayList<>(List.of(response));
            Random rand = new Random();
            for (int i = 0; i < 5; i++) {
                UserApiDTO dto = all.get(rand.nextInt(all.size()));
                User user = new User();
                user.setId(nextId++);
                user.setName(dto.name);
                user.setUsername(dto.username);
                user.setEmail(dto.email);
                user.setPhone(dto.phone);
                users.add(user);
            }
        }
    }

    public void addUser(User user) {
        user.setId(nextId++);
        users.add(0, user);
        if (users.size() > 5) {
            users.remove(5);
        }
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public void updateUser(User updatedUser) {
        users.stream()
            .filter(user -> user.getId().equals(updatedUser.getId()))
            .findFirst()
            .ifPresent(user -> {
                user.setName(updatedUser.getName());
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
            });
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    // DTO nội bộ cho parse JSON
    private static class UserApiDTO {
        public String name;
        public String username;
        public String email;
        public String phone;
    }
} 