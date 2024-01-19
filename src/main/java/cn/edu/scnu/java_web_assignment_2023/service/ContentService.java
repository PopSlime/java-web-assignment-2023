package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.*;
import cn.edu.scnu.java_web_assignment_2023.mapper.FilmMapper;
import cn.edu.scnu.java_web_assignment_2023.mapper.FilmTypeMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    /**
     * 分页查询电影。
     *
     * @param page     从 1 开始编号的分页编号。
     * @param pageSize 一页的电影数量。
     * @param ranking  排序依据。
     * @param keyword  筛选的关键词。
     * @param types    筛选的类型。
     * @return 从数据库中按所给条件查询到的电影列表。
     */
    public Page<LocalizedFilm> getFilmsPaged(int page, int pageSize, String ranking, String keyword, int[]... types) {
        String FILM_PREFIX = "film";

        // 数据库基本选择和联结操作
        MPJLambdaWrapper<Film> wrapper = new MPJLambdaWrapper<Film>(FILM_PREFIX)
                .selectAll(Film.class, FILM_PREFIX)
                .selectAs(Name::getValue, "name")
                .orderByDesc(ranking)
                .leftJoin(Name.class, Name::getNameId, Film::getNameId);

        // 按类型筛选，利用 CTE 优化查询时间
        StringBuilder sqb = null;
        for (int[] tl : types) {
            if (tl == null || tl.length == 0) continue;
            if (sqb == null) {
                sqb = new StringBuilder(" with cte as (select type_id from film_type where film_id = "
                        + FILM_PREFIX + ".film_id) select 1 where");
            } else {
                sqb.append(" and");
            }
            sqb.append(" (select count(type_id) from cte where type_id in (");
            sqb.append(Arrays.stream(tl).mapToObj(Integer::toString).collect(Collectors.joining(", ")));
            sqb.append(")) > 0");
        }
        if (sqb != null) {
            wrapper.apply(sqb.toString());
        }

        // 按关键词筛选
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Name::getValue, keyword);
        }

        // 执行查询
        return filmMapper.selectJoinPage(new Page<>(page, pageSize), LocalizedFilm.class, wrapper);
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
