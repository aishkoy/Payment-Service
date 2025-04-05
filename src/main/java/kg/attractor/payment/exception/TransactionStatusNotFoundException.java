package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class TransactionStatusNotFoundException extends NoSuchElementException {
    public TransactionStatusNotFoundException(String message) {
        super(message);
    }
}
