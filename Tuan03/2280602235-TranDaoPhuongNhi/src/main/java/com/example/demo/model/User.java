package com.example.demo.model;

import com.example.demo.dto.UserDTO;

public class User {
   private int id;
   private String name;
   private String username;
   private String email;
   private String phone;

   public User() {
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public static User fromDTO(UserDTO dto) {
      User user = new User();
      user.setId(dto.getId());
      user.setName(dto.getName());
      user.setUsername(dto.getUsername());
      user.setEmail(dto.getEmail());
      user.setPhone(dto.getPhone());
      return user;
   }
}
