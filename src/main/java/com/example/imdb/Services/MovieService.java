package com.example.imdb.Services;

import Models.MovieRatings;
import Models.Movies;
import com.example.imdb.Repos.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MovieService {
    public final Logger log = LoggerFactory.getLogger(this.getClass());
    private DB db = new DB();

    public void CreateMovie(Movies movies) {
        int id = -1;

        db.CreateAndUpdateMovies(id,movies.getName(),movies.getYear(),movies.getDuriaton(),movies.getGenre());
        db.CreaateDefaultRating(db.SelectLatestMovieID());
    }

    public void UpdateMovie(Movies movies) {

        db.CreateAndUpdateMovies(movies.getId(),movies.getName(), movies.getYear(), movies.getDuriaton(), movies.getGenre());

    }

    public Movies SelectMovie(int id){
        return db.SelectMovie(id);
    }

    public ArrayList<MovieRatings> MovieRatingList() {

        return db.MovieRatingList();
    }

    public void DeleteMovie(int id) {
        db.DeleteMovie(id);
    }

    private String makeSearch(String search) {
        String st;
        st = search.replace(" ", "|");
        log.info(st);
        return st;
    }

    public ArrayList<MovieRatings> MovieRatingListSearch(String search) {
        log.info(search);
        String searchString = makeSearch(search);
        return db.MovieRatingListSearch(searchString);
    }
}
