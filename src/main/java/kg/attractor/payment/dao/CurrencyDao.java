package kg.attractor.payment.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CurrencyDao {
    private final JdbcTemplate jdbcTemplate;

    public Long getCurrencyIdByName(String name) {
        String sql = "SELECT id FROM currencies WHERE currency = ?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Long.class, name));
    }
}
