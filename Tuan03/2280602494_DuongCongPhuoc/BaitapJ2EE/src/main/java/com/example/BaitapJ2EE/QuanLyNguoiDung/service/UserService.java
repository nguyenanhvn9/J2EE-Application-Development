package com.example.BaitapJ2EE.QuanLyNguoiDung.service;

import com.example.BaitapJ2EE.QuanLyNguoiDung.model.User;
import com.example.BaitapJ2EE.QuanLyNguoiDung.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() { return userRepository.findAll(); }
    public User getUserById(Long id) { return userRepository.findById(id); }
    public void saveUser(User user) { userRepository.save(user); }
    public void deleteUser(Long id) { userRepository.deleteById(id); }
} 