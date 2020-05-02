package repository.fileRepository;

import domain.Client;
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

public class ClientsFileRepository extends InMemoryRepository<Integer, Client> {

    private String fileName;

    public ClientsFileRepository(Validator<Client> validator, String fileName) {
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
                String name = items.get(1);
                int age = Integer.parseInt(items.get(2));
                Client client = new Client(id, name, age);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    throw new ValidatorException(e.toString(),e);
                }
            });
        } catch (IOException ex) {
            throw new FileException(ex.getMessage(),ex);
        }
    }

    public Optional<Client> update(Client entity) throws ValidatorException {
        Optional<Client> optional = super.update(entity);
        optional.ifPresentOrElse((v) -> {}, this::saveToFile);
        return optional;
    }

    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        optional.ifPresentOrElse((v) -> {}, this::saveToFile);
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            StreamSupport.stream(super.findAll().spliterator(), false).forEach(entity -> {
                        try {
                            bufferedWriter.write(entity.getId() + "," + entity.getName() + "," + entity.getAge());
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

    public Optional<Client> delete(Integer id) {
        Optional<Client> optional = super.delete(id);
        optional.ifPresentOrElse((client) -> { saveToFile(); }, () -> {});
        return optional;
    }
}







