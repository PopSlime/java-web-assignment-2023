package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(type = IdType.INPUT)
    private String userId;
    private byte[] password;
    private boolean isVip;
}
