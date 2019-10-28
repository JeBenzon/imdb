package com.example.imdb.Repos;

import Models.MovieRatings;
import Models.Ratings;
import Models.Movies;
import com.example.imdb.ProjectVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    //Connects to database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(ProjectVariables.getURL(), ProjectVariables.getUsername(), ProjectVariables.getPassword());
    }

    //can create and update movies
    public void CreateAndUpdateMovies(int id, String name, int year, int duriation, String genre) {
        String SQLString = null;
        //Tjekker om der er kaldt en Create eller en Update
        if(id>0){
            log.info("Updating");
            SQLString = "UPDATE Movies SET Movie_Name = ?, Movie_Year = ?, Movie_Duriation = ?, Movie_Genre = ? WHERE Movie_id = ?;";
        }
        if(id==-1) {
            log.info("Creating");
            SQLString = "INSERT INTO Movies (Movie_Name, Movie_Year, Movie_Duriation, Movie_Genre) VALUES(?, ?, ?, ?)";
        }

        Connection con = null;
        PreparedStatement pstms = null;
        try{
            //kalder connect() metoden.
            con = connect();
            log.info("Connection establised");
            //forbereder sql statement, og putter min SQLstring ind.
            pstms = con.prepareStatement(SQLString);
            log.info("Statement Prepared");
            //sætter de forskelige værdier i min forberedte SQLstring.
            pstms.setString(1, name);
            pstms.setInt(2, year);
            pstms.setInt(3, duriation);
            pstms.setString(4, genre);
            if(id>0){pstms.setInt(5, id);}
            log.info("String Prepared");
            //Sender kald til Database om at updatere/oprette et nyt element.
            int opret = pstms.executeUpdate();

            log.info("Query executed and Movie was succesufully added to List: Name: " + name + ", Year: " + year + ", Duriation: " + duriation + ", Genre: " + genre);
        } catch (Exception E) {
            log.error("Kunne ikke lave en QueryConnection til Create/Update");
        }
        finally {
            try { pstms.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
    }

    public Movies SelectMovie(int id) {
        Movies movies = new Movies();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    try {
        con = connect();
        pstmt = con.prepareStatement("SELECT * FROM Movies Where Movie_id = ?");
        pstmt.setInt(1,id);
        rs = pstmt.executeQuery();
        if(rs.next()){
            movies.setId(rs.getInt(1));
            movies.setName(rs.getString(2));
            movies.setYear(rs.getInt(3));
            movies.setDuriaton(rs.getInt(4));
            movies.setGenre(rs.getString(5));
        }
    } catch (SQLException e) {
        log.error("Kunne ikke lave en QueryConnection");
    }
    finally {
        try { rs.close(); } catch (Exception e) {}
        try { pstmt.close(); } catch (Exception e) {}
        try { con.close(); } catch (Exception e) {}
    }
    return movies;
    }

    //this one gets a combined list of both Movies and Ratings.
    public ArrayList<MovieRatings> MovieRatingList() {
        ArrayList<MovieRatings> movies = new ArrayList<MovieRatings>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = connect();
            pstmt = con.prepareStatement("SELECT  Movies.Movie_id, Movies.Movie_Name, Movies.Movie_Year, Movies.Movie_Duriation, Movies.Movie_Genre, ROUND(AVG(Ratings.rating),1) AS average, COUNT(distinct Rating_id) AS 'Votes'  \n" +
                    "FROM Movies LEFT JOIN Ratings ON Movies.Movie_id = Ratings.Movie_id\n" +
                    "GROUP BY Movies.Movie_id;");
            rs = pstmt.executeQuery();

            while(rs.next()) {
                MovieRatings movieRatong = new MovieRatings();
                movieRatong.setId(rs.getInt(1));
                movieRatong.setName(rs.getString(2));
                movieRatong.setYear(rs.getInt(3));
                movieRatong.setDuriaton(rs.getInt(4));
                movieRatong.setGenre(rs.getString(5));
                movieRatong.setRating(rs.getDouble(6));
                movieRatong.setVotes(rs.getInt(7));
                log.info(movieRatong.toString());
                movies.add(movieRatong);
                log.info("oneSetAddedToOpslag");
            }

        } catch (SQLException e) {
            log.error("Kunne ikke lave en QueryConnection");
        }
        finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
        return movies;
    }

    public void DeleteMovie(int id) {
        DeleteRating(id);
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = connect();
            pstmt = con.prepareStatement("DELETE FROM Movies WHERE Movie_id = ?");

            log.info("statement prepared");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            log.info("bruger med id: " + id + " er blevet slettet");
        } catch (SQLException e) {
            log.error("Kunne ikke Delete en movie");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //deleter også den pågældene rating tilknyttet til Movie

    }

    public void DeleteRating(int id) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = connect();
            pstmt = con.prepareStatement("DELETE FROM Ratings WHERE Movie_id = ?");
            log.info("statement prepared");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            log.info("Rating med id: " + id + " er blevet slettet");
        } catch (SQLException e) {
            log.error("Kunne ikke Delete en Rating");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int SelectLatestMovieID() {

        int id2 = 0;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = connect();
            pstmt = con.prepareStatement("SELECT Movie_id FROM Movies ORDER BY Movie_id DESC LIMIT 1;");
            rs = pstmt.executeQuery();
            if(rs.next()){
                id2 = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Kunne ikke lave en QueryConnection");
        }
        finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
        return id2;
}


    public Ratings SelectRatingByRatingID(int id) {
        Ratings ratings = new Ratings();

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = connect();
            pstmt = con.prepareStatement("SELECT * From Ratings \n" +
                    "Where Movie_id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                ratings.setId(rs.getInt(1));
                ratings.setMovie_id(rs.getInt(2));
                ratings.setRating(rs.getDouble(3));
            }
        } catch (SQLException e) {
            log.error("Kunne ikke lave en QueryConnection");
        }
        finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
        return ratings;
    }

    public void CreaateDefaultRating(int id) {
        Connection con = null;
        PreparedStatement pstms = null;
        try{
            con = connect();
            log.info("Connection establised");
            pstms = con.prepareStatement("INSERT INTO Ratings (Movie_id, rating) VALUES (?, ?);");
            log.info("Statement Prepared");
            pstms.setInt(1,id);
            pstms.setDouble(2, 2.5);
            int opret = pstms.executeUpdate();
        } catch (Exception E) {
            log.error("Kunne ikke lave en QueryConnection til Create/Update");
        }
        finally {
            try { pstms.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
    }

    public void CreateAndUpdateRatings(int id, int Movies_id, double Rating) {
        String SQLString = null;
        //Tjekker om der er kaldt en Create eller en Update
        if(id>0){
            log.info("Updating");
            SQLString = "UPDATE Ratings SET Movie_id = ?, rating = ? WHERE Rating_id = ?;";
        }
        if(id==-1) {
            log.info("Creating");
            CreaateDefaultRating(id);
            SQLString = "INSERT INTO Ratings (movie_id, rating) VALUES(?, ?)";
        }

        Connection con = null;
        PreparedStatement pstms = null;
        try{
            //kalder connect() metoden.
            con = connect();
            log.info("Connection establised");
            //forbereder sql statement, og putter min SQLstring ind.
            pstms = con.prepareStatement(SQLString);
            log.info("Statement Prepared");
            //sætter de forskelige værdier i min forberedte SQLstring.
            pstms.setInt(1, Movies_id);
            pstms.setDouble(2, Rating);
            if(id>0){pstms.setInt(3, id);}
            log.info("String Prepared");
            //Sender kald til Database om at updatere/oprette et nyt element.
            int opret = pstms.executeUpdate();
            log.info("Query executed and Movie was succesufully added");
        } catch (Exception E) {
            log.error("Kunne ikke lave en QueryConnection til Create/Update");
        }
        finally {
            try { pstms.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
    }

    public ArrayList<MovieRatings> MovieRatingListSearch(String search) {
        log.info(search);
        ArrayList<MovieRatings> movies = new ArrayList<MovieRatings>();
        String SQL = null;
        if(search.equals("")) {
            SQL = "SELECT  Movies.Movie_id, Movies.Movie_Name, Movies.Movie_Year, Movies.Movie_Duriation, Movies.Movie_Genre, " +
                    "ROUND(AVG(Ratings.rating),1) AS average, " +
                    "COUNT(distinct Rating_id) AS 'Votes' " +
                    "FROM Movies LEFT JOIN Ratings ON Movies.Movie_id = Ratings.Movie_id " +
                    "GROUP BY Movies.Movie_id;";
            log.info("1");
        } if(!search.equals("")) {
            log.info("2");
            SQL = "SELECT  Movies.Movie_id, Movies.Movie_Name, Movies.Movie_Year, Movies.Movie_Duriation, Movies.Movie_Genre, " +
                    "ROUND(AVG(Ratings.rating),1) AS average, " +
                    "COUNT(distinct Rating_id) AS 'Votes' " +
                    "FROM Movies LEFT JOIN Ratings ON Movies.Movie_id = Ratings.Movie_id " +
                    "WHERE Movie_Name REGEXP ? or Movie_Genre REGEXP ? or Movie_Year REGEXP ? " +
                    "GROUP BY Movies.Movie_id;";
        }

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = connect();
            pstmt = con.prepareStatement(SQL);
            if(!search.equals("")) {
                pstmt.setString(1, search);
                pstmt.setString(2, search);
                pstmt.setString(3, search);
            }
            rs = pstmt.executeQuery();

            while(rs.next()) {
                MovieRatings movieRatong = new MovieRatings();
                movieRatong.setId(rs.getInt(1));
                movieRatong.setName(rs.getString(2));
                movieRatong.setYear(rs.getInt(3));
                movieRatong.setDuriaton(rs.getInt(4));
                movieRatong.setGenre(rs.getString(5));
                movieRatong.setRating(rs.getDouble(6));
                movieRatong.setVotes(rs.getInt(7));
                log.info(movieRatong.toString());
                movies.add(movieRatong);
                log.info("oneSetAddedToOpslag");
            }

        } catch (SQLException e) {
            log.error("Kunne ikke lave en QueryConnection MovieRatingListSearch");
        }
        finally {
            try { rs.close(); } catch (Exception e) {}
            try { pstmt.close(); } catch (Exception e) {}
            try { con.close(); } catch (Exception e) {}
        }
        return movies;
    }
}

