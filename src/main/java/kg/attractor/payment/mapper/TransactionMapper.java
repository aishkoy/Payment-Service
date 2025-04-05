package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.TransactionDto;
import kg.attractor.payment.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}