package kg.attractor.payment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class TransactionLog {
    Long id;
    Long transactionId;
    Long statusId;
    Timestamp createdAt;
}
