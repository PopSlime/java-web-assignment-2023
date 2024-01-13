package cn.edu.scnu.java_web_assignment_2023.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper extends BaseMapper<user> {
    @Insert("insert into user(userID, password, isVip) values(#{account}, #{password}, false)")
    int insertUser(String account, String password);
}
