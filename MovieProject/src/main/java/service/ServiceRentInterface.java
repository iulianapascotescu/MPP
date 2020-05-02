package service;

import domain.Rent;
import domain.validators.MovieProjectException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ServiceRentInterface {
    void addRent(Rent rent) throws MovieProjectException;

    List<Rent> getAllRents();

    String findMostRentedMovie();

    HashSet<String> filterMoviesIfRented();
}
