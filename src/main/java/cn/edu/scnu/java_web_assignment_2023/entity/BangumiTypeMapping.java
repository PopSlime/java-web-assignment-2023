package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "bangumi_type")
public class BangumiTypeMapping {
    private int bangumiId;
    private int typeId;
}
