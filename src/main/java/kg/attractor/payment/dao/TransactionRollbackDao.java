package kg.attractor.payment.dao;

import kg.attractor.payment.mapper.dao.TransactionDaoMapper;
import kg.attractor.payment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionRollbackDao {
    private final JdbcTemplate jdbcTemplate;

    public Long addTransaction(Long transactionId) {
        String sql = """
                    insert into TRANSACTION_ROLLBACKS (transaction_id, created_at)
                    values (?, now())
                    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, transactionId);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Transaction> getTransactionForDeleting(Long transactionId) {
        String sql = """
                select * from TRANSACTIONS t
                inner join TRANSACTION_ROLLBACKS tr on t.id = tr.transaction_id
                where TRANSACTION_ID = ?""";
        Transaction transaction = DataAccessUtils.singleResult(jdbcTemplate.query(sql, new TransactionDaoMapper(), transactionId));
        return Optional.ofNullable(transaction);
    }
}
