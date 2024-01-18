package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "film")
public class Film {
    private int filmId;
    private int nameId;
    private String picture;
    private int descId;
    private Date date;
    private int weeklyHeat;
    private int monthlyHeat;
    private int totalHeat;
    private float rating;
}
