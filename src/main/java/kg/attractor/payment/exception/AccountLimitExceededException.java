package kg.attractor.payment.exception;

public class AccountLimitExceededException extends RuntimeException {
    public AccountLimitExceededException(String message) {
        super(message);
    }
}
