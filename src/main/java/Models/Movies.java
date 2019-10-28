package Models;

public class Movies {
    private int id;
    private String name;
    private int year;
    private int duriaton;
    private String Genre;


    public Movies() {
    }

    public Movies(int id, String name, int year, int duriaton, String genre) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.duriaton = duriaton;
        Genre = genre;
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

}
