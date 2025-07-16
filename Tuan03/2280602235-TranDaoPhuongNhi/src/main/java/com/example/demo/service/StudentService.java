package com.example.demo.service;

import com.example.demo.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
   private List<Student> students = new ArrayList<Student>();
   private int nextId = 1;

   public StudentService() {
   }

   public List<Student> getAllStudents() {
      return this.students;
   }

   public void addStudent(Student student) {
      student.setId(this.nextId++);
      this.students.add(student);
   }

   public Optional<Student> getStudentById(int id) {
      return this.students.stream().filter((s) -> {
         return s.getId() == id;
      }).findFirst();
   }

   public void updateStudent(Student updatedStudent) {
      for(int i = 0; i < this.students.size(); ++i) {
         if (((Student)this.students.get(i)).getId() == updatedStudent.getId()) {
            this.students.set(i, updatedStudent);
            return;
         }
      }

   }

   public void deleteStudent(int id) {
      this.students.removeIf((s) -> {
         return s.getId() == id;
      });
   }
}
