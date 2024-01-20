package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {
    private final ContentService service;

    public ContentController(ContentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("overallRankingList", service.getFilmsOrderedByRanking("total_heat"));
        model.addAttribute("weeklyRankingList", service.getFilmsOrderedByRanking("weekly_heat"));
        model.addAttribute("monthlyRankingList", service.getFilmsOrderedByRanking("monthly_heat"));
        model.addAttribute("reviewRankingList", service.getFilmsOrderedByRanking("rating"));
        return "/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("types", service.getFilmTypes());
        return "/index";
    }

    @GetMapping("/filmDetail")
    public String filmDetail(Model model, int id) {
        model.addAttribute("film", service.getFilmDetailById(id));
        model.addAttribute("types", service.getFilmTypesByFilmId(id));
        model.addAttribute("staffs", service.getStaffsByFilmId(id));
        return "/filmDetail";
    }

    @GetMapping("/staffDetail")
    public String staffDetail(Model model, int id) {
        model.addAttribute("staff", service.getStaffDetailById(id));
        model.addAttribute("roles", service.getRolesByStaffId(id));
        return "/staffDetail";
    }
}
