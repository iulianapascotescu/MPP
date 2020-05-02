package service;

import domain.Movie;
import domain.validators.MovieProjectException;

import java.util.ArrayList;
import java.util.List;

public interface ServiceMovieInterface {
    void addMovie(Movie movie) throws MovieProjectException;

    void deleteMovie(Integer id) throws MovieProjectException;

    void updateMovie(Movie movie) throws MovieProjectException;

    List<Movie> getAllMovies();

    List<Movie> getSortedMovies();

    Movie findOne(Integer id);
}
