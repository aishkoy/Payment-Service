package kg.attractor.payment.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
