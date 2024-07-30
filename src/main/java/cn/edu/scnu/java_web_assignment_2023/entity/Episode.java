package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "episode")
public class Episode {
    private int bangumiId;
    private int beginIndex;
    private Integer endIndex;
    private int indexOffset;
    private String indexName;
    private Date datetime;
    private int period;
}
