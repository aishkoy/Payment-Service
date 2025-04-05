package kg.attractor.payment.service;

import kg.attractor.payment.dto.UserDto;

public interface UserService {
    Long register(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto blockUser(Long id);

    Long getAuthId();
}
