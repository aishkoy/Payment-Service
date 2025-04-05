package kg.attractor.payment.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyDao {
    private final JdbcTemplate jdbcTemplate;

    public Long getCurrencyIdByName(String name) {
        String sql = "SELECT id FROM currencies WHERE currency = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, name);
    }
}
