package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.mapper.LoginMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    LoginMapper loginMapper;

    public LoginService(LoginMapper mapper) {
        loginMapper = mapper;
    }

    public Boolean authenticateUser(String account, String password){
        User user = loginMapper.selectById(account);
        return user != null && user.getPassword().equals(password);
    }
}
