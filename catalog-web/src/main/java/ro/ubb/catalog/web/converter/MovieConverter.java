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
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .year(dto.getYear())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .year(movie.getYear())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
