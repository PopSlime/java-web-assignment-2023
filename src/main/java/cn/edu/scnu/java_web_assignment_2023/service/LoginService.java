package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.mapper.LoginMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class LoginService {
    LoginMapper loginMapper;

    public LoginService(LoginMapper mapper) {
        loginMapper = mapper;
    }

    public AccountActionResult authenticateUser(String account, String password) {
        User user = loginMapper.selectById(account);
        if (user == null)
            return AccountActionResult.MISSING_ACCOUNT;
        try {
            if (!Arrays.equals(user.getPassword(), getPasswordHash(password)))
                return AccountActionResult.INCORRECT_PASSWORD;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return AccountActionResult.SUCCESS;
    }

    byte[] getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
