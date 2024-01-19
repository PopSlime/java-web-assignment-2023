package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AccountControllerAdvice {
    private final User user;

    public AccountControllerAdvice(User user) {
        this.user = user;
    }

    @ModelAttribute("user")
    User attrUser() {
        return user;
    }
}
