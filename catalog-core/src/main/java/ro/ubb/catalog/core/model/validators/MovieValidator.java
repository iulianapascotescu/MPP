package ro.ubb.catalog.core.model.validators;


import ro.ubb.catalog.core.model.Movie;

import java.util.stream.IntStream;

public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        entity.getTitle().chars().findAny().orElseThrow(() -> new ValidatorException("The title cannot be empty"));
        entity.getGenre().chars().findAny().orElseThrow(() -> new ValidatorException("The genre cannot be empty"));
        IntStream.of(entity.getYear()).filter(e -> e > 1800 && e < 2021).findAny().orElseThrow(() -> new ValidatorException("The year of the release is incorrect!"));
    }
}
