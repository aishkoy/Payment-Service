package kg.attractor.payment.model;

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

public class Transaction {
    Long id;
    Long fromAccountId;
    Long toAccountId;
    BigDecimal amount;
    Long statusId;
    Timestamp createdAt;
    Timestamp updatedAt;
}
