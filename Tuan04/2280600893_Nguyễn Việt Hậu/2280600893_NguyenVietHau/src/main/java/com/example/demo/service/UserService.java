package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public Optional<User> findById(UUID id) {
        return repo.findById(id);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
