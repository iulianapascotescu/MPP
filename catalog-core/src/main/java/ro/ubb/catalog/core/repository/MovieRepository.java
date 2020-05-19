package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieRepository extends InterfaceRepository<Movie, Integer> {
    List<Movie> findByYearIsGreaterThanEqualOrderByTitle(int year);
}
