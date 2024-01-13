package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    RegisterMapper registerMapper;

    //判断账号是否存在，true存在，false不存在
    public boolean isExist(String account)
    {
        User user = registerMapper.selectById(account);
        if(user)
        {
            return true;
        }
        return false;
    }

    //注册
    public void signUp(String account, String password)
    {
        int result = registerMapper.insertUser(account, password);

        if(result == 0) {
            System.out.println("插入失败");
        }
    }
}
