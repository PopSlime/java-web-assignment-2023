package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.LocalizedBangumi;
import cn.edu.scnu.java_web_assignment_2023.entity.LocalizedEpisode;
import cn.edu.scnu.java_web_assignment_2023.service.ContentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
public class ContentRestController {
    private final ContentService service;

    public ContentRestController(ContentService service) {
        this.service = service;
    }

    @GetMapping("/api/index")
    public Page<LocalizedBangumi> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "total_heat") String ranking,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) int[] type0,
            @RequestParam(required = false) int[] type1,
            @RequestParam(required = false) int[] type2
    ) {
        return service.getBangumiPaged(page, pageSize, ranking, keyword, type0, type1, type2);
    }

    @GetMapping("/api/stats")
    public List<LocalizedBangumi> stats(
            @RequestParam(defaultValue = "total_heat") String ranking,
            @RequestParam(defaultValue = "25") int count
    ) {
        return service.getBangumiOrderedByRanking(ranking, count);
    }

    @GetMapping("/api/index_episode")
    public List<LocalizedEpisode> index_episode(
            @RequestParam String startDateTime,
            @RequestParam(defaultValue = "100") int count
    ) {
        return service.getEpisodes(OffsetDateTime.parse(startDateTime), count);
    }
}
