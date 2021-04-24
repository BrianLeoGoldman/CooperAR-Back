package ar.edu.unq.tip.backendcooperar.model.exceptions;

public class InvalidTaskException extends Throwable {

    public InvalidTaskException(String errorMessage) {
        super(errorMessage);
    }
}
