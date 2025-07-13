package leducanh.name.vn.leducanh_2280600056.service;

import leducanh.name.vn.leducanh_2280600056.dto.User.UserDto;
import leducanh.name.vn.leducanh_2280600056.exception.ResourceNotFoundException;
import leducanh.name.vn.leducanh_2280600056.model.User;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> getAllUsers() {
        if (users.isEmpty()) {
            fetchUsersFromApi();
        }
        return new ArrayList<>(users);
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDto[] userDtos = restTemplate.getForObject(url, UserDto[].class);
        if (userDtos != null) {
            for (UserDto dto : userDtos) {
                users.add(dtoToUser(dto));
            }
        }
    }

    private User dtoToUser(UserDto dto) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getPhone());
    }

    public User getUserById(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", id));
    }

    public User addUser(User user) {
        try {
            getUserById(user.getId());
            throw new IllegalArgumentException("Người dùng với ID " + user.getId() + " đã tồn tại");
        } catch (ResourceNotFoundException e) {
            // Người dùng không tồn tại, có thể thêm mới
            users.add(user);
            return user;
        }
    }

    public User updateUser(int id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, updatedUser);
                return updatedUser;
            }
        }
        throw new ResourceNotFoundException("Người dùng", "id", id);
    }

    public boolean deleteUser(int id) {
        // Kiểm tra xem người dùng có tồn tại không
        getUserById(id); // Sẽ ném ResourceNotFoundException nếu không tìm thấy
        users.removeIf(u -> u.getId() == id);
        return true;
    }
}