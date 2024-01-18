package cn.edu.scnu.java_web_assignment_2023.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "/index";
    }

    @GetMapping("/filmDetail")
    public String filmDetail(Model model) {
        return "/filmDetail";
    }
}
