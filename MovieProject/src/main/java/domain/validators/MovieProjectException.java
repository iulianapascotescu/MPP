package domain.validators;

public class MovieProjectException extends RuntimeException {

    public MovieProjectException(String message) {
        super(message);
    }

    public MovieProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieProjectException(Throwable cause) {
        super(cause);
    }
}
