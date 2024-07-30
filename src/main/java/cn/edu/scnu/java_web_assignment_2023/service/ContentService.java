package cn.edu.scnu.java_web_assignment_2023.service;

import cn.edu.scnu.java_web_assignment_2023.entity.*;
import cn.edu.scnu.java_web_assignment_2023.mapper.BangumiMapper;
import cn.edu.scnu.java_web_assignment_2023.mapper.BangumiTypeMapper;
import cn.edu.scnu.java_web_assignment_2023.mapper.BangumiTypeMappingMapper;
import cn.edu.scnu.java_web_assignment_2023.mapper.EpisodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {
    private final BangumiMapper bangumiMapper;
    private final BangumiTypeMapper bangumiTypeMapper;
    private final BangumiTypeMappingMapper bangumiTypeMappingMapper;
    private final EpisodeMapper episodeMapper;

    public ContentService(
            BangumiMapper bangumiMapper,
            BangumiTypeMapper bangumiTypeMapper,
            BangumiTypeMappingMapper bangumiTypeMappingMapper,
            EpisodeMapper episodeMapper
    ) {
        this.bangumiMapper = bangumiMapper;
        this.bangumiTypeMapper = bangumiTypeMapper;
        this.bangumiTypeMappingMapper = bangumiTypeMappingMapper;
        this.episodeMapper = episodeMapper;
    }

    public void checkSqlCompatibility() {
        bangumiMapper.selectList(new QueryWrapper<Bangumi>().apply("with cte as (select 1) select 1").last("limit 1"));
    }

    public List<LocalizedBangumi> getBangumiOrderedByRanking(String ranking) {
        return getBangumiOrderedByRanking(ranking, 10);
    }

    public List<LocalizedBangumi> getBangumiOrderedByRanking(String ranking, int limit) {
        return bangumiMapper.selectJoinList(
                LocalizedBangumi.class,
                new MPJLambdaWrapper<Bangumi>()
                        .selectAll(Bangumi.class)
                        .selectAs(Name::getValue, "name")
                        .orderByDesc(ranking)
                        .last("limit " + limit)
                        .leftJoin(Name.class, Name::getNameId, Bangumi::getNameId)
        );
    }

    /**
     * 分页查询番剧。
     *
     * @param page     从 1 开始编号的分页编号。
     * @param pageSize 一页的番剧数量。
     * @param ranking  排序依据。
     * @param keyword  筛选的关键词。
     * @param types    筛选的类型。
     * @return 从数据库中按所给条件查询到的番剧列表。
     */
    public Page<LocalizedBangumi> getBangumiPaged(int page, int pageSize, String ranking, String keyword, int[]... types) {
        String PREFIX = "bangumi";

        // 数据库基本选择和联结操作
        MPJLambdaWrapper<Bangumi> wrapper = new MPJLambdaWrapper<Bangumi>(PREFIX)
                .selectAll(Bangumi.class, PREFIX)
                .selectAs(Name::getValue, "name")
                .orderByDesc(ranking)
                .leftJoin(Name.class, Name::getNameId, Bangumi::getNameId);

        // 按类型筛选，利用 CTE 优化查询时间
        StringBuilder sqb = null;
        for (int[] tl : types) {
            if (tl == null || tl.length == 0) continue;
            if (sqb == null) {
                sqb = new StringBuilder("with cte as (select type_id from bangumi_type where bangumi_id = "
                        + PREFIX + ".bangumi_id) select 1 where");
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
        return bangumiMapper.selectJoinPage(new Page<>(page, pageSize), LocalizedBangumi.class, wrapper);
    }

    public LocalizedBangumi getBangumiDetailById(int id) {
        return bangumiMapper.selectJoinOne(
                LocalizedBangumi.class,
                new MPJLambdaWrapper<Bangumi>()
                        .selectAll(Bangumi.class)
                        .eq(Bangumi::getBangumiId, id)
                        .selectAs(Name::getValue, "name")
                        .selectAs(Message::getValue, "description")
                        .leftJoin(Name.class, Name::getNameId, Bangumi::getNameId)
                        .leftJoin(Message.class, Message::getMsgId, Bangumi::getDescId)
        );
    }

    public Map<Integer, List<LocalizedBangumiType>> getBangumiTypesByBangumiId(int id) {
        return bangumiTypeMappingMapper.selectJoinList(
                LocalizedBangumiType.class,
                new MPJLambdaWrapper<BangumiTypeMapping>()
                        .selectAll(BangumiType.class)
                        .selectAs(Name::getValue, "name")
                        .eq(BangumiTypeMapping::getBangumiId, id)
                        .leftJoin(BangumiType.class, BangumiType::getTypeId, BangumiTypeMapping::getTypeId)
                        .leftJoin(Name.class, Name::getNameId, BangumiType::getNameId)
        ).stream().collect(Collectors.groupingBy(BangumiType::getScope));
    }

    public Map<Integer, List<LocalizedBangumiType>> getBangumiTypes() {
        return bangumiTypeMapper.selectJoinList(
                LocalizedBangumiType.class,
                new MPJLambdaWrapper<BangumiType>()
                        .selectAll(BangumiType.class)
                        .selectAs(Name::getValue, "name")
                        .leftJoin(Name.class, Name::getNameId, BangumiType::getNameId)
        ).stream().collect(Collectors.groupingBy(BangumiType::getScope));
    }

    public List<LocalizedEpisode> getEpisodes(OffsetDateTime startDateTime, Integer bangumiId, int limit) {
        MPJLambdaWrapper<Episode> wrapper = new MPJLambdaWrapper<Episode>()
                .selectAll(Episode.class)
                .selectAs(Name::getValue, "name")
                .selectAs(Bangumi::getPicture, "picture");
        if (bangumiId != null) wrapper.eq(Episode::getBangumiId, bangumiId);
        if (startDateTime != null) wrapper.ge(Episode::getDatetime, startDateTime);
        wrapper.orderByAsc(Episode::getDatetime)
                .last("limit " + limit)
                .leftJoin(Bangumi.class, Bangumi::getBangumiId, Episode::getBangumiId)
                .leftJoin(Name.class, Name::getNameId, Bangumi::getNameId);
        return episodeMapper.selectJoinList(LocalizedEpisode.class, wrapper);
    }
}
