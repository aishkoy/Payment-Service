package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class TransactionNotFoundException extends NoSuchElementException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
