package com.example.imdb.Services;

import Models.Ratings;
import com.example.imdb.Repos.DB;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private DB db = new DB();

    public Ratings SelectRatingByRatingIDwhenRating(int id) {
        Ratings rating = new Ratings();
        rating = db.SelectRatingByRatingID(id);
        rating.setRating(0);
        return  rating;
    }

    public void CreateRating(Ratings ratings) {
        int id = -1;
        db.CreateAndUpdateRatings(id, ratings.getMovie_id(), ratings.getRating());
    }

}


