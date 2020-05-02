package service;


import Utils.Sort;
import domain.Movie;
import domain.Rent;
import domain.validators.SQLRepoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import repository.dbRepository.ClientDBRepository;
import repository.dbRepository.MovieDBRepository;
import repository.dbRepository.RentDBRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceMovie implements ServiceMovieInterface {
    public static final Logger log = LoggerFactory.getLogger(ServiceMovie.class);
    @Autowired
    private MovieDBRepository repositoryMovie;
    @Autowired
    private ClientDBRepository repositoryClient;
    @Autowired
    private RentDBRepository repositoryRent;

    public ServiceMovie() {
    }

    @Override
    public List<Movie> getAllMovies() {
        log.trace("getAllMovies - method entered");
        return repositoryMovie.findAll();
    }

    @Override
    public void addMovie(Movie movie) {
        log.trace("addMovie - method entered: movie={}", movie);
        repositoryMovie.save(movie);
        log.trace("addMovie - method finished");
    }

    @Override
    @Transactional
    public void updateMovie(Movie movie) {
        log.trace("updateMovie - method entered: movie={}", movie);
        repositoryMovie.findById(movie.getId())
                .ifPresent(s -> {
                    s.setTitle(movie.getTitle());
                    s.setGenre(movie.getGenre());
                    s.setYear(movie.getYear());
                    log.debug("updateMovie - updated: s={}", s);
                });
        log.trace("updateMovie - method finished");
    }

    @Override
    public void deleteMovie(Integer id) {
        log.trace("deleteMovie - method entered: id={}", id);
        List<Rent> rents = repositoryRent.findAll().stream().filter(p -> p.getMovieId()==id).collect(Collectors.toList());
        rents.stream().forEach(p -> repositoryRent.deleteById(p.getId()));
        repositoryMovie.deleteById(id);
        log.trace("deleteMovie - method finished");
    }

    public List<Movie> getSortedMovies(){
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

    @Override
    public Movie findOne(Integer id) {
        log.trace("findOne - method entered: id={}", id);
        return this.repositoryMovie.findById(id).get();
    }
}
