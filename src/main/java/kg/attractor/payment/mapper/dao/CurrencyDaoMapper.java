package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyDaoMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency currency = new Currency();
        currency.setId(rs.getLong("id"));
        currency.setCurrency(rs.getString("currency"));
        return null;
    }
}
