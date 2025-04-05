package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class AccountNotFoundException extends NoSuchElementException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
