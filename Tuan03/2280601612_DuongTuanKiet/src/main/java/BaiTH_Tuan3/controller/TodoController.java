package BaiTH_Tuan3.controller;

import BaiTH_Tuan3.model.TodoItem;
import BaiTH_Tuan3.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Trang chính hiển thị danh sách todo (có thể lọc: all / active / completed)
    @GetMapping
    public String index(@RequestParam(required = false, defaultValue = "all") String filter, Model model) {
        model.addAttribute("todoList", todoService.getAllItems(filter));
        model.addAttribute("newTodo", new TodoItem());
        model.addAttribute("activeCount", todoService.countActiveItems());
        model.addAttribute("currentFilter", filter); // 👈 quan trọng để tránh lỗi Thymeleaf
        return "index";
    }

    // Thêm công việc mới
    @PostMapping("/add")
    public String add(@ModelAttribute("newTodo") TodoItem item) {
        todoService.addItem(item.getTitle());
        return "redirect:/todos";
    }

    // Chuyển trạng thái hoàn thành của công việc
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        todoService.toggleCompleted(id);
        return "redirect:/todos";
    }

    // Xóa một công việc
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        todoService.deleteItem(id);
        return "redirect:/todos";
    }

    // Xóa toàn bộ công việc đã hoàn thành
    @PostMapping("/clear-completed")
    public String clearCompleted() {
        todoService.clearCompleted();
        return "redirect:/todos";
    }
}
