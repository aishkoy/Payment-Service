package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.CurrencyConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyConverterDaoMapper implements RowMapper<CurrencyConverter> {
    @Override
    public CurrencyConverter mapRow(ResultSet rs, int rowNum) throws SQLException {
        CurrencyConverter currencyConverter = new CurrencyConverter();
        currencyConverter.setFromCurrencyId(rs.getLong("from_currency_id"));
        currencyConverter.setToCurrencyId(rs.getLong("to_currency_id"));
        currencyConverter.setRate(rs.getBigDecimal("rate"));
        return null;
    }
}
