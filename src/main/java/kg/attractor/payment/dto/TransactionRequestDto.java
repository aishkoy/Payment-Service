package kg.attractor.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class TransactionRequestDto {
    @NotNull
    Long fromAccountId;
    @NotNull
    Long toAccountId;
    @NotNull @Positive
    BigDecimal amount;
}
