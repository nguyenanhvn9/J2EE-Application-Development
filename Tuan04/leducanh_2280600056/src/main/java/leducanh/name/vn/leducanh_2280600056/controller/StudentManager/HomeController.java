package leducanh.name.vn.leducanh_2280600056.controller.StudentManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student-manager")
public class HomeController {
    @GetMapping()
    public String index(){
       return "StudentManager/index";
    }
}
