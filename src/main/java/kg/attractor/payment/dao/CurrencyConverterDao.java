package kg.attractor.payment.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CurrencyConverterDao {
    private final JdbcTemplate jdbcTemplate;

    public BigDecimal getRate(Long fromCurrency, Long toCurrency) {
        String sql = """
                select rate from currency_converter
                            where from_currency_id = ?
                            and to_currency_id = ?""";
        return Objects.requireNonNull(
                jdbcTemplate.queryForObject(
                        sql, BigDecimal.class, fromCurrency, toCurrency));
    }
}
