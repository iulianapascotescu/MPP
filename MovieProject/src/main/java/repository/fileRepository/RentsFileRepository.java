
package repository.fileRepository;

import domain.Rent;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import domain.validators.FileException;
import repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class RentsFileRepository extends InMemoryRepository<Integer, Rent> {
    private String fileName;

    public RentsFileRepository(Validator<Rent> validator, String fileName) {
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
                int IdMovie = Integer.parseInt(items.get(1));
                int IdClient = Integer.parseInt(items.get(2));
                Rent rent = new Rent(id, IdMovie, IdClient);
                try {
                    super.save(rent);
                } catch (ValidatorException e) {
                    throw new ValidatorException(e.toString(),e);
                }
            });
        } catch (IOException ex) {
            throw new FileException(ex.toString(),ex);
        }
    }

    public Optional<Rent> save(Rent entity) throws ValidatorException {
        Optional<Rent> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, this::saveToFile);
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE)) {
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(entity -> {
                        try {
                            bufferedWriter.write(entity.getId() + "," + entity.getMovieId() + "," + entity.getClientId());
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
}
