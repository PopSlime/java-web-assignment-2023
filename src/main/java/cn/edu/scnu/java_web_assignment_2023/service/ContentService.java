package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.Film;
import cn.edu.scnu.java_web_assignment_2023.entity.LocalizedFilm;
import cn.edu.scnu.java_web_assignment_2023.entity.Name;
import cn.edu.scnu.java_web_assignment_2023.mapper.FilmMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {
    FilmMapper filmMapper;

    public ContentService(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    public List<LocalizedFilm> getFilmsOrderedByRanking(String ranking) {
        return getFilmsOrderedByRanking(ranking, 10);
    }

    public List<LocalizedFilm> getFilmsOrderedByRanking(String ranking, int limit) {
        return filmMapper.selectJoinList(
                LocalizedFilm.class,
                new MPJLambdaWrapper<Film>()
                        .selectAs(Name::getValue, "name")
                        .orderByDesc(ranking)
                        .last("limit " + limit)
                        .leftJoin(Name.class, Name::getNameId, Film::getNameId)
        );
    }
}
