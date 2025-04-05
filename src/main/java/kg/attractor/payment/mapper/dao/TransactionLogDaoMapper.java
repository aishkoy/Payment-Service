package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.TransactionLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionLogDaoMapper implements RowMapper<TransactionLog> {
    @Override
    public TransactionLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setId(rs.getLong("id"));
        transactionLog.setTransactionId(rs.getLong("transaction_id"));
        transactionLog.setStatusId(rs.getLong("status_id"));
        transactionLog.setCreatedAt(rs.getTimestamp("created_at"));
        return null;
    }
}
