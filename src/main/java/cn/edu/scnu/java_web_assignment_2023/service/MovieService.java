package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.Movie;
import cn.edu.scnu.java_web_assignment_2023.mapper.MovieMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.rmi.server.SkeletonMismatchException;
import java.util.List;

@Service
public class MovieService {

    MovieMapper movieMapper;

    public MovieService(MovieMapper mapper) {
        this.movieMapper = mapper;
    }

    public List<Movie> findAll(String keyword)
    {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        //添加数据库中的keyword在下面
        queryWrapper.eq("", keyword);
        return movieMapper.selectList(queryWrapper);
    }
}
