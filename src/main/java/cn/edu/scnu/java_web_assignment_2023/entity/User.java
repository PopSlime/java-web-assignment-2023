package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
@TableName(value = "user")
public class User {
    @TableId(type = IdType.INPUT)
    private String userId;
    private byte[] password;
    private boolean isVip;

    public void copyTo(User other) {
        other.setUserId(userId);
        other.setPassword(password);
        other.setVip(isVip);
    }

    public void reset() {
        setUserId(null);
        setPassword(null);
        setVip(false);
    }
}
