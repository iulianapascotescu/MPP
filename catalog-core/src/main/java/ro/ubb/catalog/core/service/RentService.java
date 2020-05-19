package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RentService implements RentServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Pageable page;

    @Override
    @Transactional
    public Rent saveRent(@NotNull @Valid Rent rent) {
        log.trace("saveRent - method entered: rent={}", rent);
        this.movieRepository.findById(rent.getMovie().getId()).ifPresent(
                m -> {
                    List<Rent> rents = m.getRents();
                    rents.add(rent);
                    m.setRents(rents);
                }
        );
        this.clientRepository.findById(rent.getClient().getId()).ifPresent(
                c -> {
                    List<Rent> rents = c.getRents();
                    rents.add(rent);
                    c.setRents(rents);
                }
        );
        return rent;
    }

    @Override
    public List<Rent> getAllRents() {
        log.trace("RentService getAllRents: method entered");
        List<Rent> rents = new ArrayList<>();
        List<Movie> movies = this.movieRepository.findAll();
        for (Movie movie : movies)
            rents.addAll(movie.getRents());
        log.trace("RentService getAllRents: result={}", rents);
        return rents;
    }

    @Override
    public Movie findMostRentedMovie() {
        /*
        log.trace("findMostRentedMovie - method entered");
        Iterable<Rent> rents = rentRepository.findAll();
        List<Integer> rentedMoviesIds = StreamSupport.stream(rents.spliterator(), false).map(Rent::getMovieId).collect(Collectors.toList());
        return this.movieRepository.findById(rentedMoviesIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey()).get().getTitle();
        */
        return null;
    }

}
