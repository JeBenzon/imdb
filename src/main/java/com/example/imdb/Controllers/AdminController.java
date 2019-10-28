package com.example.imdb.Controllers;

import com.example.imdb.Models.MovieRatings;
import com.example.imdb.Models.Movies;
import com.example.imdb.Services.MovieService;
import com.example.imdb.Services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    MovieService movieService;
    @Autowired
    RatingService ratingService;

    @GetMapping("/admin")
    public String admin(Model model, @RequestParam(defaultValue = "") String search) {
        List<MovieRatings> movies = movieService.MovieRatingListSearch(search);
        model.addAttribute("movies", movies);
        return "/views/admin/admin";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("movies", new Movies());
        return "/views/admin/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Movies movies) {
        movieService.CreateMovie(movies);
        return "redirect:/admin";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("movies", movieService.SelectMovie(id));
        return "/views/admin/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Movies movies, Model model){
        movieService.UpdateMovie(movies);
        model.addAttribute("movies", movieService.MovieRatingList());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("movies", movieService.SelectMovie(id));
        return "/views/admin/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Movies movies) {
        movieService.DeleteMovie(movies.getId());
        return "redirect:/admin";
    }
}
