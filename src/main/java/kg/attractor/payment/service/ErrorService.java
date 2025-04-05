package kg.attractor.payment.service;

import jakarta.validation.ConstraintViolation;
import kg.attractor.payment.exception.handler.ErrorResponseBody;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;

import java.util.Set;

public interface ErrorService {
    ErrorResponseBody makeResponse(IllegalStateException e);

    ErrorResponseBody makeResponse(IllegalArgumentException e);

    ErrorResponseBody makeResponse(DataIntegrityViolationException e);

    ErrorResponseBody makeResponse(Exception e);

    ErrorResponseBody makeResponse(BindingResult bindingResult);

    ErrorResponseBody makeResponse(Set<ConstraintViolation<?>> constraintViolations);
}
