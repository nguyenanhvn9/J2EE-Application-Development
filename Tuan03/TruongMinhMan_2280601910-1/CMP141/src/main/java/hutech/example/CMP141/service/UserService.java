package hutech.example.CMP141.service;

import hutech.example.CMP141.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final String url = "https://jsonplaceholder.typicode.com/users";
    private final List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    public UserService() {
        users.add(new User(nextId++, "Nguyen Van A", "nguyenvana", "a@gmail.com", "0123456789"));
        users.add(new User(nextId++, "Tran Thi B", "tranthib", "b@gmail.com", "0987654321"));
        users.add(new User(nextId++, "Le Van C", "levanc", "c@gmail.com", "0111222333"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        user.setId(nextId++);
        users.add(user);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public void updateUser(User updatedUser) {
        users.stream()
            .filter(u -> u.getId().equals(updatedUser.getId()))
            .findFirst()
            .ifPresent(u -> {
                u.setName(updatedUser.getName());
                u.setUsername(updatedUser.getUsername());
                u.setEmail(updatedUser.getEmail());
                u.setPhone(updatedUser.getPhone());
            });
    }

    public void deleteUser(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
} 