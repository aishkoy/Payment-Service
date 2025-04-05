package kg.attractor.payment.service;

import kg.attractor.payment.dto.UserDto;

public interface UserService {
    Long register(UserDto userDto);
}
