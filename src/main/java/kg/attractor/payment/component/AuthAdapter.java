package kg.attractor.payment.component;

import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AuthAdapter {
    private final UserService service;

    public UserDto getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new NoSuchElementException("user not authorized");
        }
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new IllegalArgumentException("user not authorized");
        }

        String username = authentication.getName();
        UserDto user = service.getUserByEmail(username);

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return user;
    }

    public Long getAuthId(){
        return getAuthUser().getId();
    }
}