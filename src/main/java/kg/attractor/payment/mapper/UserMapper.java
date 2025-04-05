package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.UserDto;
import kg.attractor.payment.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}