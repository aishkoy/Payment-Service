package kg.attractor.payment.service;

public interface TransactionStatusService {
    Long getStatusPending();

    Long getStatusApproved();

    Long getStatusDeleted();

    Long getStatusRollback();

    Long getStatusCompleted();
}
