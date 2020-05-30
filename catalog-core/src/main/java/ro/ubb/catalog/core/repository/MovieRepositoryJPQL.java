package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieRepositoryJPQL {
    void updateMovie(Movie movie);
    List<Movie> findAllWithRentsAndClients();
}
