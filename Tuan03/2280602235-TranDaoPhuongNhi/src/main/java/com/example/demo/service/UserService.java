package com.example.demo.service;

import com.example.demo.dto.UserDTO; 
import com.example.demo.model.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
   private final RestTemplate restTemplate;
   private final String apiUrl;
   private final List<User> users = new CopyOnWriteArrayList<User>();

   public UserService(RestTemplate restTemplate, @Value("${api.users.url}") String apiUrl) {
      this.restTemplate = restTemplate;
      this.apiUrl = apiUrl;
   }

   public List<User> getAllUsers() {
      if (this.users.isEmpty()) {
         this.fetchUsersFromApi();
      }

      return new ArrayList<User>(this.users);
   }

   public User getUserById(int id) {
      return (User)this.users.stream().filter((user) -> {
         return user.getId() == id;
      }).findFirst().orElse(null);
   }

   public User addUser(User user) {
      int maxId = this.users.stream().mapToInt(User::getId).max().orElse(0);
      user.setId(maxId + 1);
      this.users.add(user);
      return user;
   }

   public void updateUser(int id, User userDetails) {
      for(int i = 0; i < this.users.size(); ++i) {
         if (((User)this.users.get(i)).getId() == id) {
            userDetails.setId(id);
            this.users.set(i, userDetails);
            return;
         }
      }

   }

   public boolean deleteUser(int id) {
      return this.users.removeIf((user) -> {
         return user.getId() == id;
      });
   }

   private void fetchUsersFromApi() {
      UserDTO[] userDTOs = (UserDTO[])this.restTemplate.getForObject(this.apiUrl, UserDTO[].class, new Object[0]);
      if (userDTOs != null) {
         Stream var10000 = Arrays.stream(userDTOs).map(User::fromDTO);
         List var10001 = this.users;
         var10000.forEach(var10001::add);
      }

   }
}
