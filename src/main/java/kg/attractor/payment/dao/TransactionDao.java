package kg.attractor.payment.dao;

import kg.attractor.payment.mapper.dao.TransactionDaoMapper;
import kg.attractor.payment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        String sql = """
                select * from transactions
                where from_account_id = ? or to_account_id = ?
                order by created_at desc
                """;
        return jdbcTemplate.query(sql, new TransactionDaoMapper(), accountId, accountId);
    }

    public Long addTransaction(Transaction transaction) {
        String sql = """
                    insert into transactions (from_account_id,
                                to_account_id, amount, status_id, created_at, updated_at)
                    values (?, ?, ?, ?, now(), now())
                    """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, transaction.getFromAccountId());
            ps.setLong(2, transaction.getToAccountId());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setLong(4, transaction.getStatusId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
