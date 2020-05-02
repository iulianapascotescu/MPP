package service;

import domain.Movie;
import domain.Rent;
import domain.validators.MovieProjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import repository.dbRepository.ClientDBRepository;
import repository.dbRepository.MovieDBRepository;
import repository.dbRepository.RentDBRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceRent implements ServiceRentInterface {
    public static final Logger log = LoggerFactory.getLogger(ServiceRent.class);
    @Autowired
    private MovieDBRepository repositoryMovie;
    @Autowired
    private ClientDBRepository repositoryClient;
    @Autowired
    private RentDBRepository repositoryRent;

    public ServiceRent() {
    }

    @Override
    public void addRent(Rent rent) throws MovieProjectException {
        log.trace("addRent - method entered: rent={}", rent);
        repositoryRent.save(rent);
        log.trace("addRent - method finished");
    }

    @Override
    public List<Rent> getAllRents() {
        log.trace("getAllRents - method entered");
        return repositoryRent.findAll();
    }

    public HashSet<String> filterMoviesIfRented() {
        log.trace("filterMoviesIfRented - method entered");
        Iterable<Movie> movies = repositoryMovie.findAll();
        Iterable<Rent> rents = repositoryRent.findAll();
        Set<Integer> rentedMoviesIds = StreamSupport.stream(rents.spliterator(), false).map(Rent::getMovieId).collect(Collectors.toSet());
        return StreamSupport.stream(movies.spliterator(), false).filter(movie -> rentedMoviesIds.contains(movie.getId())).map(Movie::getTitle).collect(Collectors.toCollection(HashSet::new));
    }

    public String findMostRentedMovie() {
        log.trace("findMostRentedMovie - method entered");
        Iterable<Rent> rents = repositoryRent.findAll();
        List<Integer> rentedMoviesIds = StreamSupport.stream(rents.spliterator(), false).map(Rent::getMovieId).collect(Collectors.toList());
        return this.repositoryMovie.findById(rentedMoviesIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()).get().getTitle();
    }
}
