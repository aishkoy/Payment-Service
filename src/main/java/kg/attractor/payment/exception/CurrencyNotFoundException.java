package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class CurrencyNotFoundException extends NoSuchElementException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
