package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.TransactionLogDto;
import kg.attractor.payment.model.TransactionLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionLogMapper {
    TransactionLogDto toDto(TransactionLog transactionLog);
    TransactionLog toEntity(TransactionLogDto transactionLogDto);
}