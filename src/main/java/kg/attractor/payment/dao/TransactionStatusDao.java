package kg.attractor.payment.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionStatusDao {
    private final JdbcTemplate jdbcTemplate;

    public Long getStatusByName(String name){
        String sql = "select id from transaction_statuses where status=?";
        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }
}
