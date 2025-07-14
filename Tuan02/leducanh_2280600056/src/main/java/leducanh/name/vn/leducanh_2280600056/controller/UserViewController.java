package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.User;
import leducanh.name.vn.leducanh_2280600056.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserService userService;

    @Autowired
    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<User> allUsers = userService.getAllUsers();
        int totalPages = (int) Math.ceil((double) allUsers.size() / size);

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allUsers.size());
        List<User> users = allUsers.subList(fromIndex, toIndex);

        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);
        return "users";
    }

    @PostMapping
    public String addOrUpdateUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            if (user.getId() == 0) {
                throw new IllegalArgumentException("ID không được để trống");
            }
            userService.addUser(user);
            redirectAttributes.addFlashAttribute("msg", "Thêm người dùng thành công!");
        } catch (IllegalArgumentException e) {
            userService.updateUser(user.getId(), user);
            redirectAttributes.addFlashAttribute("msg", "Cập nhật người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Lỗi: " + e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        User user = userService.getUserById(id);
        List<User> allUsers = userService.getAllUsers();
        int totalPages = (int) Math.ceil((double) allUsers.size() / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allUsers.size());
        List<User> users = allUsers.subList(fromIndex, toIndex);

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);
        return "users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("msg", "Xoá người dùng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "Lỗi: " + e.getMessage());
        }
        return "redirect:/users";
    }
}
