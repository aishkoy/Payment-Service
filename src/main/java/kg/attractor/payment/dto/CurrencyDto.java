package kg.attractor.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class CurrencyDto {
    Long id;
    @NotBlank
    @Size(min = 3, max = 3,
            message = "Введите инициалы валюты длиной в три символа.")
    String currency;
}
