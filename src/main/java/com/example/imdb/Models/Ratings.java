package com.example.imdb.Models;

public class Ratings {
    private int id;
    private int movie_id;
    private double rating;

    public Ratings() {
    }

    public Ratings(int id, int movie_id, double rating) {
        this.id = id;
        this.movie_id = movie_id;
        this.rating = rating;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Ratings{" +
                "id=" + id +
                ", movie_id=" + movie_id +
                ", rating=" + rating +
                '}';
    }
}
