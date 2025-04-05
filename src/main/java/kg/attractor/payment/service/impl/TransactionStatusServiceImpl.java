package kg.attractor.payment.service.impl;

import kg.attractor.payment.dao.TransactionStatusDao;
import kg.attractor.payment.exception.TransactionStatusNotFoundException;
import kg.attractor.payment.service.TransactionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionStatusServiceImpl implements TransactionStatusService {
    private final TransactionStatusDao dao;

    public Long getStatusIdByName(String name) {
        try {
            return dao.getStatusByName(name.trim().toUpperCase());
        } catch (EmptyResultDataAccessException e) {
            throw new TransactionStatusNotFoundException("Status with name " + name + " not found");
        }
    }

    @Override
    public Long getStatusPending() {
        return getStatusIdByName("PENDING");
    }

    @Override
    public Long getStatusDeleted() {
        return getStatusIdByName("DELETED");
    }

    @Override
    public Long getStatusRolledBack() {
        return getStatusIdByName("ROLLED_BACK");
    }

    @Override
    public Long getStatusCompleted() {
        return getStatusIdByName("COMPLETED");
    }
}
