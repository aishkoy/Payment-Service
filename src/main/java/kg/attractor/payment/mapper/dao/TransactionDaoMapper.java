package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDaoMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(rs.getLong("id"));
        transaction.setFromAccountId(rs.getLong("from_account_id"));
        transaction.setToAccountId(rs.getLong("to_account_id"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setStatusId(rs.getLong("status_id"));
        transaction.setCreatedAt(rs.getTimestamp("created_at"));
        transaction.setUpdatedAt(rs.getTimestamp("updated_at"));
        return transaction;
    }
}
