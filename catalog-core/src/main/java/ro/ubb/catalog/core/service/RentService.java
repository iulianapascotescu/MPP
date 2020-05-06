package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;
import ro.ubb.catalog.core.repository.RentRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentService implements RentServiceInterface{
    public static final Logger log= LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RentRepository rentRepository;

    @Override
    public Rent saveRent(Rent rent) {
        log.trace("saveRent - method entered: rent={}", rent);
        Rent savedRent = this.rentRepository.save(rent);
        log.trace("saveRent - method finished");
        return savedRent;
    }

    @Override
    public List<Rent> getAllRents() {
        log.trace("getAllRents: method entered");
        List<Rent> rents = rentRepository.findAll();
        log.trace("getAllRents: result={}", rents);
        return rents;
    }

    @Override
    public String findMostRentedMovie() {
        log.trace("findMostRentedMovie - method entered");
        Iterable<Rent> rents = rentRepository.findAll();
        List<Integer> rentedMoviesIds = StreamSupport.stream(rents.spliterator(), false).map(Rent::getMovieId).collect(Collectors.toList());
        return this.movieRepository.findById(rentedMoviesIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()).get().getTitle();
    }

    @Override
    public HashSet<String> filterMoviesIfRented() {
        log.trace("filterMoviesIfRented - method entered");
        Iterable<Movie> movies = movieRepository.findAll();
        Iterable<Rent> rents = rentRepository.findAll();
        Set<Integer> rentedMoviesIds = StreamSupport.stream(rents.spliterator(), false).map(Rent::getMovieId).collect(Collectors.toSet());
        return StreamSupport.stream(movies.spliterator(), false).filter(movie -> rentedMoviesIds.contains(movie.getId())).map(Movie::getTitle).collect(Collectors.toCollection(HashSet::new));
    }
}
