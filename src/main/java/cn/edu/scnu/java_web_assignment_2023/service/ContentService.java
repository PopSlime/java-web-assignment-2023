package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.*;
import cn.edu.scnu.java_web_assignment_2023.mapper.FilmMapper;
import cn.edu.scnu.java_web_assignment_2023.mapper.FilmTypeMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {
    private final FilmMapper filmMapper;
    private final FilmTypeMapper filmTypeMapper;

    public ContentService(FilmMapper filmMapper, FilmTypeMapper filmTypeMapper) {
        this.filmMapper = filmMapper;
        this.filmTypeMapper = filmTypeMapper;
    }

    public List<LocalizedFilm> getFilmsOrderedByRanking(String ranking) {
        return getFilmsOrderedByRanking(ranking, 10);
    }

    public List<LocalizedFilm> getFilmsOrderedByRanking(String ranking, int limit) {
        return filmMapper.selectJoinList(
                LocalizedFilm.class,
                new MPJLambdaWrapper<Film>()
                        .selectAll(Film.class)
                        .selectAs(Name::getValue, "name")
                        .orderByDesc(ranking)
                        .last("limit " + limit)
                        .leftJoin(Name.class, Name::getNameId, Film::getNameId)
        );
    }

    public Map<Integer, List<LocalizedFilmType>> getFilmTypes() {
        return filmTypeMapper.selectJoinList(
                LocalizedFilmType.class,
                new MPJLambdaWrapper<FilmType>()
                        .selectAll(FilmType.class)
                        .selectAs(Name::getValue, "name")
                        .leftJoin(Name.class, Name::getNameId, FilmType::getNameId)
        ).stream().collect(Collectors.groupingBy(FilmType::getScope));
    }
}
