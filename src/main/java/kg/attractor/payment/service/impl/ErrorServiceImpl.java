package kg.attractor.payment.service.impl;

import jakarta.validation.ConstraintViolation;
import kg.attractor.payment.exception.handler.ErrorResponseBody;
import kg.attractor.payment.service.ErrorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import java.util.*;

@Service
public class ErrorServiceImpl implements ErrorService {
    @Override
    public ErrorResponseBody makeResponse(IllegalStateException e) {
        String message = Optional.ofNullable(e.getMessage())
                .orElse("Обнаружено недопустимое состояние");

        return ErrorResponseBody.builder()
                .title("Ошибка состояния")
                .response(Map.of("Ошибка ", List.of(message)))
                .build();
    }


    @Override
    public ErrorResponseBody makeResponse(IllegalArgumentException e) {
        String message = Optional.ofNullable(e.getMessage())
                .orElse("Передан недопустимый аргумент");

        return ErrorResponseBody.builder()
                .title("Ошибка аргумента")
                .response(Map.of("Ошибка ", List.of(message)))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(DataIntegrityViolationException e) {
        String message = e.getMessage();

        return ErrorResponseBody.builder()
                .title("Ошибка валидации")
                .response(Map.of("Ошибка ", List.of(message)))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(Exception e) {
        String message = e.getMessage();
        return ErrorResponseBody.builder()
                .title(message)
                .response(Map.of("Ошибка ", List.of(message)))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult bindingResult) {
        Map<String, List<String>> reasons = new HashMap<>();
        bindingResult.getFieldErrors().stream()
                .filter(error -> error.getDefaultMessage() != null)
                .forEach(err -> {
                    if (!reasons.containsKey(err.getField())) {
                        reasons.put(err.getField(), new ArrayList<>());
                    }
                    reasons.get(err.getField()).add(err.getDefaultMessage());
                });
        return ErrorResponseBody.builder()
                .title("Ошибка валидации")
                .response(reasons)
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, List<String>> reasons = new HashMap<>();
        constraintViolations.forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            reasons.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        return ErrorResponseBody.builder()
                .title("Ошибка валидации")
                .response(reasons)
                .build();
    }
}
