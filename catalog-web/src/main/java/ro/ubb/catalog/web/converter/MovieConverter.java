package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.web.dto.MovieDto;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    public MovieConverter() {
    }

    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = new Movie(dto.getTitle(),dto.getGenre(),dto.getYear(),null);
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto movieDto = new MovieDto(movie.getTitle(),movie.getGenre(),movie.getYear());
        movieDto.setId(movie.getId());
        return movieDto;}
}
