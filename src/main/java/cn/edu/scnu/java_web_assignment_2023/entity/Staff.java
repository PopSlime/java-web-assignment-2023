package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "staff")
public class Staff {
    @TableId
    private Integer staffId;
    private int nameId;
    private String picture;
    private int descId;

    @TableField(exist = false)
    private int role = Integer.MIN_VALUE;
}
