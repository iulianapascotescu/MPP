package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;

import java.util.HashSet;
import java.util.List;

public interface RentServiceInterface {
    Rent saveRent(Rent rent);

    List<Rent> getAllRents();

    String findMostRentedMovie();

    HashSet<String> filterMoviesIfRented();
}
