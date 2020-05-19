package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.Rent;
import ro.ubb.catalog.core.repository.ClientRepository;
import ro.ubb.catalog.core.repository.MovieRepository;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MovieService implements MovieServiceInterface {
    public static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Pageable pageForSort;
    private Pageable pageForFilter;

    @Override
    public List<Movie> getAllMovies() {
        log.trace("MovieService getAllMovies: method entered");
        List<Movie> movies = movieRepository.findAll();
        log.trace("MovieService getAllMovies: result={}", movies);
        return movies;
    }

    @Override
    public Movie saveMovie(@NotNull @Valid Movie movie) {
        log.trace("saveMovie - method entered: movie={}", movie);
        Movie savedMovie = this.movieRepository.save(movie);
        log.trace("saveMovie - method finished");
        return savedMovie;
    }

    @Override
    @Transactional
    public Movie updateMovie(@NotNull @Valid Movie movie) {
        log.trace("updateMovie - method entered: movie={}", movie);
        Movie update = movieRepository.findById(movie.getId()).orElse(movie);
        this.movieRepository.save(movie);
        log.trace("updateMovie - method finished");
        return update;
    }

    @Override
    @Transactional
    public void deleteMovie(@Min(0) Integer id) {
        log.trace("deleteMovie - method entered: id={}", id);
        Movie movie = movieRepository.findById(id).get();
        for (Rent rent : movie.getRents()) {
            Client client = rent.getClient();
            rent.setClient(null);
            rent.setMovie(null);
            this.clientRepository.findById(client.getId()).ifPresent(c -> {
                List<Rent> rents = c.getRents();
                rents.remove(rent);
                c.setRents(rents);
            });
        }
        movieRepository.deleteById(id);
        log.trace("deleteMovie - method finished");
    }

    @Override
    public Movie findById(@Min(0) int id) {
        return this.movieRepository.findById(id).get();
    }

    @Override
    public List<Movie> sort() {
        this.pageForSort = PageRequest.of(0, 3,
                org.springframework.data.domain.Sort.by("title"));
        return this.movieRepository.findAll(this.pageForSort).getContent();
    }

    @Override
    public List<Movie> nextSort() {
        this.pageForSort = this.pageForSort.next();
        List<Movie> movies = this.movieRepository.findAll(this.pageForSort).getContent();
        if (movies.size() == 0) {
            this.pageForSort = this.pageForSort.previousOrFirst();
            movies = this.movieRepository.findAll(this.pageForSort).getContent();
        }
        return movies;
    }

    @Override
    public List<Movie> previousSort() {
        this.pageForSort = this.pageForSort.previousOrFirst();
        return this.movieRepository.findAll(this.pageForSort).getContent();
    }

    @Override
    public List<Movie> filterMovies() {
        log.trace("filterMovies - method entered");
        this.pageForFilter = PageRequest.of(0, 2);
        List<Movie> result = this.movieRepository.findByYearIsGreaterThanEqualOrderByTitle(2019);
        Page<Movie> pages = new PageImpl<>(result.subList(0, 2), this.pageForFilter, result.size());
        return pages.getContent();
    }

    @Override
    public List<Movie> nextFilterMovies() {
        log.trace("nextFilterMovies - method entered");
        this.pageForFilter = this.pageForFilter.next();
        List<Movie> result = this.movieRepository.findByYearIsGreaterThanEqualOrderByTitle(2019);
        int start = (int) this.pageForFilter.getOffset();
        int end = Math.min((start + pageForFilter.getPageSize()), result.size());
        Page<Movie> pages = new PageImpl<>(result.subList(start, end), this.pageForFilter, result.size());
        return pages.getContent();
    }

    @Override
    public List<Movie> prevFilterMovies() {
        log.trace("prevFilterMovies - method entered");
        this.pageForFilter = this.pageForFilter.previousOrFirst();
        List<Movie> result = this.movieRepository.findByYearIsGreaterThanEqualOrderByTitle(2019);
        int start = (int) this.pageForFilter.getOffset();
        int end = Math.min((start + pageForFilter.getPageSize()), result.size());
        Page<Movie> pages = new PageImpl<>(result.subList(start, end), this.pageForFilter, result.size());
        return pages.getContent();
    }

}
