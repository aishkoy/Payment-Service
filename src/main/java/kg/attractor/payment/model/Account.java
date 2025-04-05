package kg.attractor.payment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class Account {
    Long id;
    Long userId;
    Long currencyId;
    BigDecimal balance;
}
