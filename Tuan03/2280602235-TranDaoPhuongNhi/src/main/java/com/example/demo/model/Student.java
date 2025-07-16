package com.example.demo.model;

public class Student {
   private int id;
   private String name;
   private String email;
   private String major;

   public Student() {
   }

   public Student(int id, String name, String email, String major) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.major = major;
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

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getMajor() {
      return this.major;
   }

   public void setMajor(String major) {
      this.major = major;
   }
}
