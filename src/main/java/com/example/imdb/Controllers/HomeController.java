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
        return "views/IMDB";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, Model model) {

        model.addAttribute("movies", movieService.SelectMovie(id));
        return "views/details";
    }

    @GetMapping("rate/{id}")
    public String rate(@PathVariable int id, Model model) {
        //ratingService.CreaateDefaultRating(id);
        model.addAttribute("rating", ratingService.SelectRatingByRatingIDwhenRating(id));
        return "views/rate";
    }

    @PostMapping("/rate")
    public String rate(@ModelAttribute Ratings ratings) {
        ratingService.CreateRating(ratings);
        return "redirect:/";
    }


}
