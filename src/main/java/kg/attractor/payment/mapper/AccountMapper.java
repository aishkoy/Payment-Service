package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.AccountDto;
import kg.attractor.payment.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
}