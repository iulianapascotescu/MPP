package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findAllWithRentsAndClientsJPQL();

    List<Movie> findAllWithRentsAndClientsCriteriaAPI();

    List<Movie> findAllWithRentsAndClientsSQL();


    void updateMovieJPQL(Movie movie);
    void updateMovieCriteriaAPI(Movie movie);
    void updateMovieSQL(Movie movie);

    Movie addMovieJPQL(Movie movie);
}
