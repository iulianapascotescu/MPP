package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.service.MovieServiceInterface;
import ro.ubb.catalog.core.utils.Sort;
import ro.ubb.catalog.web.converter.MovieConverter;
import ro.ubb.catalog.web.dto.MovieDto;
import ro.ubb.catalog.web.dto.MoviesDto;

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
    public List<MovieDto> getMovies(){
        log.trace("MoviesDto getMovies - method entered");
        List<MovieDto> moviesDto = new ArrayList(movieConverter
                .convertModelsToDtos(movieService.getAllMovies()));
        log.trace("MoviesDto getMovies - method finished");
        return moviesDto;
    }

    @RequestMapping(value = "/movies/sorted", method = RequestMethod.GET)
    public MoviesDto getSortedMovies(){
        log.trace("MoviesDto getSortedMovies - method entered");
        MoviesDto moviesDto = new MoviesDto(movieConverter
                .convertModelsToDtos(movieService.getSortedMovies()));
        log.trace("MoviesDto getSortedMovies - method finished");
        return moviesDto;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public MovieDto saveMovie(@RequestBody MovieDto movieDto) {
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
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id){
        log.trace("ResponseEntity<?> deleteMovie: id={} - method entered", id);
        movieService.deleteMovie(id);
        log.trace("ResponseEntity<?> deleteMovie - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
