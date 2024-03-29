package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "name")
public class Name {
    @TableId
    private Integer nameId;
    private String lang;
    private boolean isNative;
    private String value;
}
