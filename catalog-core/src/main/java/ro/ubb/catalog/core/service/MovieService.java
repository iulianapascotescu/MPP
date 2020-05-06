package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.model.validators.SQLRepoException;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;
import ro.ubb.catalog.core.repository.RentRepository;
import ro.ubb.catalog.core.utils.Sort;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieService implements MovieServiceInterface{
    public static final Logger log= LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RentRepository rentRepository;

    @Override
    public List<Movie> getAllMovies() {
        log.trace("getAllMovies: method entered");
        List<Movie> movies = movieRepository.findAll();
        log.trace("getAllMovies: result={}", movies);
        return movies;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        log.trace("saveMovie - method entered: movie={}", movie);
        Movie savedMovie = this.movieRepository.save(movie);
        log.trace("saveMovie - method finished");
        return savedMovie;
    }

    @Override
    public Movie updateMovie(Movie movie) {
        log.trace("updateMovie - method entered: movie={}", movie);
        Movie update = movieRepository.findById(movie.getId()).orElse(movie);
        this.movieRepository.save(movie);
        log.trace("updateMovie - method finished");
        return update;
    }

    @Override
    public void deleteMovie(Integer id) {
        log.trace("deleteMovie - method entered: id={}", id);
        List<Rent> rents = rentRepository.findAll().stream().filter(p -> p.getMovieId()==id).collect(Collectors.toList());
        rents.stream().forEach(p -> rentRepository.deleteById(p.getId()));
        movieRepository.deleteById(id);
        log.trace("deleteMovie - method finished");
    }

    @Override
    public List<Movie> getSortedMovies() {
        log.trace("getSortedMovies - method entered");
        Sort sort = new Sort("title"); //sort asc by title
        //Sort sort = new Sort("genre"); //sort asc by genre

        //sort desc by genre and desc by title (should work with any number of fields)
        //Sort sort = new Sort(Sort.direction.DESC, "genre", "title");
        //sort desc by genre and asc by title
        //Sort sort = new Sort(Sort.direction.DESC, "genre").and(new Sort("title"));

        ArrayList<Movie> result = (ArrayList<Movie>) getAllMovies();
        List<Sort> sortList = sort.getSortList();
        sortList.forEach((s) -> {
            List<String> fieldsList = s.getFields();
            fieldsList.forEach(p -> result.sort((c1, c2) -> {
                try {
                    Field f1 = c1.getClass().getDeclaredField(p);
                    f1.setAccessible(true);
                    String n1 = (String) f1.get(c1);
                    Field f2 = c2.getClass().getDeclaredField(p);
                    f2.setAccessible(true);
                    String n2 = (String) f2.get(c2);
                    AtomicInteger comp = new AtomicInteger(n1.compareTo(n2));
                    Stream.of(s.getCurrentDirection()).filter(c->!c).anyMatch((v)->{ comp.set(comp.get() * -1); return v;});
                    return comp.get();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new SQLRepoException(e.getMessage(), e);
                }
            }));
        });
        return result;
    }

}
