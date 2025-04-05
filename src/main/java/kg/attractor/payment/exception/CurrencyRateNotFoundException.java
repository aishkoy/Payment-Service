package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class CurrencyRateNotFoundException extends NoSuchElementException {
    public CurrencyRateNotFoundException(String message) {
        super(message);
    }
}
