package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.TransactionRollback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRollbackDaoMapper implements RowMapper<TransactionRollback> {
    @Override
    public TransactionRollback mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionRollback transactionRollback = new TransactionRollback();
        transactionRollback.setId(rs.getLong("id"));
        transactionRollback.setTransactionId(rs.getLong("transaction_id"));
        transactionRollback.setCreatedAt(rs.getTimestamp("created_at"));
        return null;
    }
}
