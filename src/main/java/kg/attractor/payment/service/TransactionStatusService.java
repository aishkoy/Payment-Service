package kg.attractor.payment.service;

public interface TransactionStatusService {
    Long getStatusPending();

    Long getStatusDeleted();

    Long getStatusRolledBack();

    Long getStatusCompleted();
}
