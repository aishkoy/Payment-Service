package kg.attractor.payment.mapper.dao;

import kg.attractor.payment.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setUserId(rs.getLong("user_id"));
        account.setCurrencyId(rs.getLong("currency_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return null;
    }
}
