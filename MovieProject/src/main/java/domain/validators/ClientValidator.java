package domain.validators;

import domain.Client;

import java.util.stream.IntStream;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        entity.getName().chars().findAny().orElseThrow(() -> new ValidatorException("The name cannot be empty"));
        IntStream.of(entity.getAge()).filter(e -> e < 130 && e > 0).findAny().orElseThrow(() -> new ValidatorException("The age of the client is incorrect"));
    }
}
