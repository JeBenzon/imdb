package com.example.imdb.Controllers;

import com.example.imdb.Models.MovieRatings;
import com.example.imdb.Models.Ratings;
import com.example.imdb.Models.Movies;
import com.example.imdb.Services.MovieService;
import com.example.imdb.Services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    MovieService movieService;
    @Autowired
    RatingService ratingService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "") String search) {
        List<MovieRatings> movies = movieService.MovieRatingListSearch(search);
        model.addAttribute("movies", movies);
        return "IMDB";
    }

    @GetMapping("/admin")
    public String admin(Model model, @RequestParam(defaultValue = "") String search) {
        List<MovieRatings> movies = movieService.MovieRatingListSearch(search);
        model.addAttribute("movies", movies);
        return "admin";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model) {

        model.addAttribute("movies", movieService.SelectMovie(id));
        return "details";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("movies", new Movies());
        return "create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Movies movies) {
        movieService.CreateMovie(movies);
        return "redirect:/admin";
    }

    @GetMapping("rate/{id}")
    public String rate(@PathVariable int id, Model model) {
        model.addAttribute("rating", ratingService.SelectRatingByRatingIDwhenRating(id));
        return "Rate";
    }

    @PostMapping("/rate")
    public String rate(@ModelAttribute Ratings ratings) {
        ratingService.CreateRating(ratings);

        return "redirect:/";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("movies", movieService.SelectMovie(id));
        return "edit";
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
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Movies movies) {
        movieService.DeleteMovie(movies.getId());
        return "redirect:/admin";
    }



}
