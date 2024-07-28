package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "episode")
public class Episode {
    private int bangumiId;
    private int index;
    private String indexName;
    private Date datetime;
}
