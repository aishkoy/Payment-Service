package kg.attractor.payment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class TransactionDto {
    Long id;
    Long fromAccountId;
    Long toAccountId;
    BigDecimal amount;
    Long statusId;
    Timestamp createdAt;
    Timestamp updatedAt;
}
