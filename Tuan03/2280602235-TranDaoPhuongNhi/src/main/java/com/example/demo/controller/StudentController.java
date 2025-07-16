package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/students"})

public class StudentController {
    @Autowired
   private StudentService studentService;

   public StudentController() {
   }

   @GetMapping
   public String listStudents(Model model) {
      model.addAttribute("students", this.studentService.getAllStudents());
      return "students";
   }

   @GetMapping({"/add"})
   public String addStudentForm(Model model) {
      model.addAttribute("student", new Student());
      return "add-student";
   }

   @PostMapping({"/add"})
   public String addStudent(@ModelAttribute Student student) {
      this.studentService.addStudent(student);
      return "redirect:/students";
   }

   @GetMapping({"/edit/{id}"})
   public String editStudentForm(@PathVariable int id, Model model) {
      this.studentService.getStudentById(id).ifPresent((student) -> {
         model.addAttribute("student", student);
      });
      return "edit-student";
   }

   @PostMapping({"/edit"})
   public String updateStudent(@ModelAttribute Student student) {
      this.studentService.updateStudent(student);
      return "redirect:/students";
   }

   @GetMapping({"/delete/{id}"})
   public String deleteStudent(@PathVariable int id) {
      this.studentService.deleteStudent(id);
      return "redirect:/students";
   }
}
