package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "film_type")
public class FilmTypeMapping {
    private int filmId;
    private int typeId;
}
