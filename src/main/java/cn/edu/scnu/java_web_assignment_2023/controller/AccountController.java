package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.service.AccountActionResult;
import cn.edu.scnu.java_web_assignment_2023.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String account, String password, Model model) {
        AccountActionResult result = service.authenticateUser(account, password);
        if (result == AccountActionResult.SUCCESS)
            return "redirect:/"; // 回到主页面
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
        if (result == AccountActionResult.SUCCESS)
            return "redirect:/"; // 回到主页面
        model.addAttribute("error", "account.error." + result.getLocaleKey());
        return "signup";
    }
}
