package BaiTH_03.service;

import BaiTH_03.dto.UserDTO;
import BaiTH_03.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers() {
//        if (users.isEmpty()) {
//            fetchUsersFromApi();
//        }
        return users;
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public boolean addUser(User user) {
        if (getUserById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User with this ID already exists.");
        }
        users.add(user);
        return true;
    }

    public Optional<User> updateUser(int id, User updatedUser) {
        Optional<User> existing = getUserById(id);
        if (existing.isEmpty()) return Optional.empty();

        User user = existing.get();
        user.setName(updatedUser.getName());
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        return Optional.of(user);
    }

    public boolean deleteUser(int id) {
        Optional<User> user = getUserById(id);
        return user.map(users::remove).orElse(false);
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDTO[] dtoArray = restTemplate.getForObject(url, UserDTO[].class);
        if (dtoArray != null) {
            for (UserDTO dto : dtoArray) {
                users.add(new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getEmail(), dto.getPhone()));
            }
        }
    }
}