package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/users"})

public class UserController {
    private final UserService userService;

   @Autowired
   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping
   public List<User> getAllUsers() {
      return this.userService.getAllUsers();
   }

   @GetMapping({"/{id}"})
   public ResponseEntity<User> getUserById(@PathVariable int id) {
      User user = this.userService.getUserById(id);
      return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
   }

   @PostMapping
   public ResponseEntity<User> createUser(@RequestBody User user) {
      User createdUser = this.userService.addUser(user);
      return ResponseEntity.ok(createdUser);
   }

   @PutMapping({"/{id}"})
   public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
      this.userService.updateUser(id, user);
      return ResponseEntity.ok().build();
   }

   @DeleteMapping({"/{id}"})
   public ResponseEntity<Void> deleteUser(@PathVariable int id) {
      return this.userService.deleteUser(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
   }
}
