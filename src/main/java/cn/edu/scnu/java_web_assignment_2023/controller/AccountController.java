package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.service.AccountActionResult;
import cn.edu.scnu.java_web_assignment_2023.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    AccountService service;
    User user;

    public AccountController(AccountService service, User user) {
        this.service = service;
        this.user = user;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String account, String password, Model model) {
        AccountActionResult result = service.authenticateUser(account, password);
        if (result.isSuccess()) {
            result.getUser().copyTo(user);
            return "redirect:/"; // 回到主页面
        }
        model.addAttribute("error", "account.error." + result.getLocaleKey());
        return "login";
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(String account, String password, Model model) {
        AccountActionResult result = service.signUp(account, password);
        if (result.isSuccess())
            return "redirect:/"; // 回到主页面
        model.addAttribute("error", "account.error." + result.getLocaleKey());
        return "signup";
    }

    @GetMapping("/logout")
    public String logout() {
        user.reset();
        return "redirect:/"; // 回到主页面
    }
}
