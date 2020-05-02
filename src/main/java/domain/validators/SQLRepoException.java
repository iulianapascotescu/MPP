package domain.validators;

public class SQLRepoException extends MovieProjectException {
    public SQLRepoException(String message) {
        super(message);
    }

    public SQLRepoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRepoException(Throwable cause) {
        super(cause);
    }
}
