package com.hutech.User.service;

import com.hutech.User.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserService() {
        addUser(new User(null, "Nguyen Van A", "a@gmail.com"));
        addUser(new User(null, "Tran Thi B", "b@gmail.com"));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public void addUser(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
    }

    public User findById(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        User existing = findById(updatedUser.getId());
        if (existing != null) {
            existing.setName(updatedUser.getName());
            existing.setEmail(updatedUser.getEmail());
        }
    }

    public void deleteById(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}
