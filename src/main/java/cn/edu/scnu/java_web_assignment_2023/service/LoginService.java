package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginMapper loginMapper;

    public Boolean authenticateUser(String account, String password){
        User user = loginMapper.selectById(account);
        return user != null && user.getPassword().equals(password);
    }
}
