package cn.edu.scnu.java_web_assignment_2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "bangumi_staff")
public class BangumiStaffMapping {
    private int bangumiId;
    private int staffId;
    private int role;
}
