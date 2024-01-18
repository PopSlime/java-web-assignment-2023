package cn.edu.scnu.java_web_assignment_2023.mapper;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<User> {
}
