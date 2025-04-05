package kg.attractor.payment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class User {
    Long id;
    String name;
    String phoneNumber;
    String email;
    String password;
    Long roleId;
    Boolean enabled;
}
