package kg.attractor.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class CurrencyConverterDto {
    Long fromCurrencyId;
    Long toCurrencyId;
    BigDecimal rate;
}
