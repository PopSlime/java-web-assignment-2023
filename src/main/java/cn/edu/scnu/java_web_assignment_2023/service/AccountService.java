package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class AccountService {
    AccountMapper mapper;

    public AccountService(AccountMapper mapper) {
        this.mapper = mapper;
    }

    public AccountActionResult authenticateUser(String account, String password) {
        User user = mapper.selectById(account);
        if (user == null)
            return AccountActionResult.MISSING_ACCOUNT;
        try {
            if (!Arrays.equals(user.getPassword(), getPasswordHash(password)))
                return AccountActionResult.INCORRECT_PASSWORD;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new AccountActionResult(user);
    }

    public AccountActionResult signUp(String account, String password) {
        User user = mapper.selectById(account);
        if (user != null)
            return AccountActionResult.DUPLICATE_ACCOUNT;

        user = new User();
        user.setUserId(account);
        try {
            user.setPassword(getPasswordHash(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        int result = mapper.insert(user);

        if (result == 0)
            return AccountActionResult.INTERNAL;
        return new AccountActionResult(user);
    }

    byte[] getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
