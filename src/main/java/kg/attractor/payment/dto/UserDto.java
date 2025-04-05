package kg.attractor.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class UserDto {
    Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+$",
            message = """
                    Имя должно содержать только русские
                    и английские буквы без пробелов""")
    String name;

    @NotNull
    @Pattern(regexp = "996 \\(\\d{3}\\) \\d{2}-\\d{2}-\\d{2}",
            message = """
                    Номер телефона должен быть в формате
                    996 (XXX) XX-XX-XX""")
    String phoneNumber;

    @NotNull
    @Email
    String email;

    @NotNull
    @Size(min = 6, max = 20,
            message = "Пароль должен быть длиной от 6 до 20 сиволов")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
            message = """
                    Пароль должен содержать хотя бы
                    одну заглавную букву и хотя бы одну цифру.""")
    String password;

    Long roleId;
    Boolean enabled;
}
