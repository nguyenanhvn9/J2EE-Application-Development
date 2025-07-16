package leducanh.name.vn.leducanh_2280600056.service;

import leducanh.name.vn.leducanh_2280600056.dto.User.UserDto;
import leducanh.name.vn.leducanh_2280600056.exception.ResourceNotFoundException;
import leducanh.name.vn.leducanh_2280600056.model.User;

import leducanh.name.vn.leducanh_2280600056.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        if (userRepository.count() == 0) {
            fetchUsersFromApi();
        }
        return userRepository.findAll();
    }

    private void fetchUsersFromApi() {
        String url = "https://jsonplaceholder.typicode.com/users";
        UserDto[] userDtos = restTemplate.getForObject(url, UserDto[].class);
            for (UserDto dto : userDtos) {
                userRepository.save(dtoToUser(dto));
        }
    }

    private User dtoToUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return user;
    }


    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", id));
    }

    public User addUser(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("Người dùng với ID " + user.getId() + " đã tồn tại");
        }
        return userRepository.save(user);
    }

    public User updateUser(int id, User updatedUser) {
        User existing = getUserById(id); // managed entity

        existing.setName(updatedUser.getName());
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());

        return userRepository.save(existing);
    }



    public boolean deleteUser(int id) {
        getUserById(id);
        userRepository.deleteById(id);
        return true;
    }
}
