package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Student;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.StudentClass;
import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Subject;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.StudentClassService;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("student-manager/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentClassService classService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("classes", classService.findAll());
        // THÊM DÒNG NÀY:
        model.addAttribute("student", new Student());
        return "StudentManager/student";
    }


    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/student-manager/students";
    }
    @GetMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, Model model) {
       Student student = studentService.findById(id).orElseThrow();
       model.addAttribute("student", student);
       model.addAttribute("classes", classService.findAll());
        return "StudentManager/student";
    }
    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, @ModelAttribute Student student) {
        student.setId(id);
        studentService.save(student);
        return "redirect:/student-manager/students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/student-manager/students";
    }
}
