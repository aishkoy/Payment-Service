package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.CurrencyConverterDto;
import kg.attractor.payment.model.CurrencyConverter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyConverterMapper {
    CurrencyConverterDto toDto(CurrencyConverter currencyConverter);
    CurrencyConverter toEntity(CurrencyConverterDto currencyConverterDto);
}