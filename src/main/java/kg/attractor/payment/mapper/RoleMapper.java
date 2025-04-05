package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.RoleDto;
import kg.attractor.payment.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}