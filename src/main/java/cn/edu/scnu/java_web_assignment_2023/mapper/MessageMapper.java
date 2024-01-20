package cn.edu.scnu.java_web_assignment_2023.mapper;

import cn.edu.scnu.java_web_assignment_2023.entity.Message;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper extends MPJBaseMapper<Message> {
}
