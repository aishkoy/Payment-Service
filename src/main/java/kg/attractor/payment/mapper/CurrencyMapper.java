package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.CurrencyDto;
import kg.attractor.payment.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toDto(Currency currency);
    Currency toEntity(CurrencyDto currencyDto);
}