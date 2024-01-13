package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @GetMapping("/signup")
    public String showSignup(Model model)
    {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSigup(String account, String password, Model model){
        //判断账号是否存在
        if(registerService.isExist(account))
        {
           model.addAttribute("Error", "Account already exists");
           return "signup";
        }

        registerService.signUp(account, password);
        return "signup";

    }
}
