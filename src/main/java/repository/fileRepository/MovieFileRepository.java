package repository.fileRepository;

import domain.Movie;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import domain.validators.FileException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class MovieFileRepository extends InMemoryRepository<Integer, Movie> {
    private String fileName;

    public MovieFileRepository(Validator<Movie> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                int id = Integer.parseInt(items.get(0));
                String title = items.get(1);
                String genre = items.get((2));
                int year = Integer.parseInt(items.get(3));

                Movie movie = new Movie(id, title, genre, year);
                movie.setId(id);

                try {
                    super.save(movie);
                } catch (ValidatorException e) {
                    throw new ValidatorException(e.toString(),e);
                }
            });
        } catch (IOException ex) {
            throw new FileException(ex.getMessage(),ex);
        }
    }


    public Optional<Movie> save(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, this::saveToFile);
        return optional;
    }

    public Optional<Movie> update(Movie entity) throws ValidatorException {
        Optional<Movie> optional = super.update(entity);
        optional.ifPresentOrElse((v) -> {}, this::saveToFile);
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(entity -> {
                        try {
                            bufferedWriter.write(entity.getId() + "," + entity.getTitle() + "," + entity.getGenre() + "," + entity.getYear());
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            throw new FileException(e.getMessage(),e);
                        }
                    }
            );
        } catch (IOException e) {
            throw new FileException(e.getMessage(),e);
        }
    }

    public Optional<Movie> delete(Integer id) {
        Optional<Movie> optional = super.delete(id);
        optional.ifPresentOrElse((movie) -> { saveToFile(); }, () -> {});
        return optional;
    }
}



