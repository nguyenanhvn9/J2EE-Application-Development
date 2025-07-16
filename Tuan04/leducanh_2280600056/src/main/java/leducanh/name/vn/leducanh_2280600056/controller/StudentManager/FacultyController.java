package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import leducanh.name.vn.leducanh_2280600056.model.StudentManager.Faculty;
import leducanh.name.vn.leducanh_2280600056.service.StudentManager.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("student-manager/faculties")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public String listAndEdit(
            @RequestParam(required = false) Long id,
            Model model
    ) {
        // Danh sách tất cả khoa
        model.addAttribute("faculties", facultyService.findAll());

        // Chuẩn bị đối tượng cho form
        Faculty formObject = (id != null)
                ? facultyService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Khoa id=" + id))
                : new Faculty();

        model.addAttribute("faculty", formObject);
        return "StudentManager/faculties";   // chính là file templates/StudentManager/faculties.html
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid faculty Id:" + id));
        model.addAttribute("faculty", faculty);
        model.addAttribute("faculties", facultyService.findAll());
        return "StudentManager/faculties"; // Trang vừa hiển thị danh sách vừa có form thêm/sửa
    }

    /**
     * Lưu khi người dùng submit form.
     */
    @PostMapping("/add")
    public String addFaculty(@ModelAttribute Faculty faculty) {
        facultyService.save(faculty);
        return "redirect:/student-manager/faculties";
    }

    @PostMapping("/edit/{id}")
    public String updateFaculty(@PathVariable Long id, @ModelAttribute Faculty faculty) {
        Faculty existing = facultyService.findById(id).orElseThrow();
        existing.setName(faculty.getName());
        facultyService.save(existing);
        return "redirect:/student-manager/faculties";
    }
    /**
     * Xóa khoa theo id
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        facultyService.deleteById(id);
        return "redirect:/student-manager/faculties";
    }
}