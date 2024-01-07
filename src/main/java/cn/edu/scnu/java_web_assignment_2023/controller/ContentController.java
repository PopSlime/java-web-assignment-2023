package cn.edu.scnu.java_web_assignment_2023.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    @GetMapping("/")
    public String index(Model model) {
        return "/home";
    }
}
