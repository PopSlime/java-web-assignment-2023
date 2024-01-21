package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.Movie;
import cn.edu.scnu.java_web_assignment_2023.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MovieController {

    MovieService movieService;
    Movie movie;

    public MovieController(MovieService service, Movie movie) {
        this.movieService = service;
        this.movie = movie;
    }

    @PostMapping("/filter")
    public String MovieList(String keyword, Model model){
        List<Movie> movies;

        movies =  movieService.findAll(keyword);
        model.addAttribute("movies", movies);
        return "/index";
    }

}
