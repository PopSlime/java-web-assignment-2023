package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "type")
public class FilmType {
    @TableId
    private Integer typeId;
    private int nameId;
    private int scope;
}
