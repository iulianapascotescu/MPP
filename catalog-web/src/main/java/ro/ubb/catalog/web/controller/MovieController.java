package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.service.MovieServiceInterface;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.MoviesDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieServiceInterface movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<MovieDto> getMovies() {
        log.trace("MovieController getMovies - method entered");
        List<MovieDto> moviesDto = new ArrayList(movieConverter
                .convertModelsToDtos(movieService.getAllMovies()));
        log.trace("MovieController getMovies - method finished: movies{}", moviesDto);
        return moviesDto;
    }

    @RequestMapping(value = "/movies/sorted", method = RequestMethod.GET)
    public MoviesDto getSortedMovies() {
        log.trace("MoviesDto getSortedMovies - method entered");
        //MoviesDto moviesDto = new MoviesDto(movieConverter
        //.convertModelsToDtos(movieService.getSortedMovies()));
        log.trace("MoviesDto getSortedMovies - method finished");
        return null;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public MovieDto saveMovie(@RequestBody @NotNull @Valid MovieDto movieDto) {
        log.trace("MovieDto saveMovie: movieDto={} - method entered", movieDto);
        MovieDto movieDtoSaved = movieConverter.convertModelToDto(movieService.saveMovie(
                movieConverter.convertDtoToModel(movieDto)));
        log.trace("MovieDto saveMovie - method finished");
        return movieDtoSaved;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    public MovieDto updateMovie(@PathVariable Integer id, @RequestBody MovieDto movieDto) {
        log.trace("MovieDto updateMovie: movieDto={} - method entered", movieDto);
        MovieDto movieDtoUpdated = movieConverter.convertModelToDto(movieService.updateMovie(
                movieConverter.convertDtoToModel(movieDto)));
        log.trace("MovieDto updateMovie - method finished");
        return movieDtoUpdated;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMovie(@PathVariable @Min(0) Integer id) {
        log.trace("ResponseEntity<?> deleteMovie: id={} - method entered", id);
        movieService.deleteMovie(id);
        log.trace("ResponseEntity<?> deleteMovie - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/sort", method = RequestMethod.GET)
    public List<MovieDto> sortMovies() {
        log.trace("MoviesDto sortMovies - method entered");
        List<MovieDto> moviesDto = new ArrayList(movieConverter
                .convertModelsToDtos(movieService.sort()));
        log.trace("MoviesDto sortMovies - method finished {}", moviesDto);
        return moviesDto;
    }

    @RequestMapping(value = "/movies/nextSort", method = RequestMethod.GET)
    public List<MovieDto> nextSortMovies() {
        log.trace("MoviesDto nextSortMovies - method entered");
        List<MovieDto> moviesDto = new ArrayList(movieConverter
                .convertModelsToDtos(movieService.nextSort()));
        log.trace("MoviesDto nextSortMovies - method finished {}", moviesDto);
        return moviesDto;
    }

    @RequestMapping(value = "/movies/prevSort", method = RequestMethod.GET)
    public List<MovieDto> prevSortMovies() {
        log.trace("MoviesDto prevSortMovies - method entered");
        List<MovieDto> moviesDto = new ArrayList(movieConverter
                .convertModelsToDtos(movieService.previousSort()));
        log.trace("MoviesDto prevSortMovies - method finished {}", moviesDto);
        return moviesDto;
    }

    @RequestMapping(value = "/movies/filter", method = RequestMethod.GET)
    public List<MovieDto> filterMovies() {
        log.trace("List<String> filterMovies - method entered");
        List<MovieDto> result = new ArrayList(movieConverter.convertModelsToDtos(this.movieService.filterMovies()));
        log.trace("List<String> filterMovies - method finished");
        return result;
    }

    @RequestMapping(value = "/movies/nextFilter", method = RequestMethod.GET)
    public List<MovieDto> nextFilterMovies() {
        log.trace("List<String> filterMovies - method entered");
        List<MovieDto> result = new ArrayList(movieConverter.convertModelsToDtos(this.movieService.nextFilterMovies()));
        log.trace("List<String> filterMovies - method finished");
        return result;
    }

    @RequestMapping(value = "/movies/prevFilter", method = RequestMethod.GET)
    public List<MovieDto> prevFilterMovies() {
        log.trace("List<String> filterMovies - method entered");
        List<MovieDto> result = new ArrayList(movieConverter.convertModelsToDtos(this.movieService.prevFilterMovies()));
        log.trace("List<String> filterMovies - method finished");
        return result;
    }

}
