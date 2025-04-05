package kg.attractor.payment.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
