package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "film_staff")
public class FilmStaffMapping {
    private int filmId;
    private int staffId;
    private int role;
}
