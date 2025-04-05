package kg.attractor.payment.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<Long> registerUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ofNullable(userService.register(userDto));
    }
}
