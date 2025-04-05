package kg.attractor.payment.mapper;

import kg.attractor.payment.dto.TransactionRollbackDto;
import kg.attractor.payment.model.TransactionRollback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionRollbackMapper {
    TransactionRollbackDto toDto(TransactionRollback transactionRollback);
    TransactionRollback toEntity(TransactionRollbackDto transactionRollbackDto);
}