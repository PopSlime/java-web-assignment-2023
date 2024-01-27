package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
    private final AccountService service;
    private final User user;

    public AccountRestController(AccountService service, User user) {
        this.service = service;
        this.user = user;
    }

    @PostMapping("/api/subscribe")
    public String subscribe() {
        if (user.getUserId() == null)
            return "forbidden";
        user.setVip(true);
        service.update(user);
        return "ok";
    }
}
