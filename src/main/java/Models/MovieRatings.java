package Models;

public class MovieRatings {
    private int id;
    private String name;
    private int year;
    private int duriaton;
    private String Genre;
    private double rating;
    private int votes;

    public MovieRatings() {
    }

    public MovieRatings(int id, String name, int year, int duriaton, String genre, double rating, int votes) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.duriaton = duriaton;
        Genre = genre;
        this.rating = rating;
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuriaton() {
        return duriaton;
    }

    public void setDuriaton(int duriaton) {
        this.duriaton = duriaton;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    @Override
    public String toString() {
        return "movies{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", duriaton=" + duriaton +
                ", Genre='" + Genre + '\'' +
                ", rating=" + rating +
                ", votes=" + votes +
                '}';
    }
}
