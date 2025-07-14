package com.example.BaitapJ2EE.QuanLyNguoiDung.repository;

import com.example.BaitapJ2EE.QuanLyNguoiDung.model.User;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long nextId = 1;

    public UserRepository() {
        users.add(new User(nextId++, "Nguyen Van A", "nguyenvana", "a@gmail.com", "0123456789"));
        users.add(new User(nextId++, "Tran Thi B", "tranthib", "b@gmail.com", "0987654321"));
    }

    public List<User> findAll() { return users; }
    public User findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
            users.add(user);
        } else {
            User existing = findById(user.getId());
            if (existing != null) {
                existing.setName(user.getName());
                existing.setUsername(user.getUsername());
                existing.setEmail(user.getEmail());
                existing.setPhone(user.getPhone());
            }
        }
    }
    public void deleteById(Long id) {
        users.removeIf(u -> u.getId().equals(id));
    }
} 