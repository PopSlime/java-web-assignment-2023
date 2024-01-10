package cn.edu.scnu.java_web_assignment_2023;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String account, String password, Model model){
        if(loginService.authenticateUser(account, password)){
            return "redirect:/home";                      //回到主页面
        }
        else{
            model.addAttribute("Error", "Invalid username or password");
            return "login";
        }
    }
}
