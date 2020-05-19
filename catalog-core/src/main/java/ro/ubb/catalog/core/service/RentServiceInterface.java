package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;

import java.util.Collection;
import java.util.List;

public interface RentServiceInterface {
    Rent saveRent(Rent rent);

    List<Rent> getAllRents();

    Movie findMostRentedMovie();

}
