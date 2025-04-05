package kg.attractor.payment.dao;

import kg.attractor.payment.mapper.dao.AccountDaoMapper;
import kg.attractor.payment.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Account> getUserAccounts(Long userId) {
        String sql = "select * from account where user_id=?";
        return jdbcTemplate.query(sql, new AccountDaoMapper(), userId);
    }

    public Long create(Long userId, Long currencyId) {
        String sql = """
                insert into accounts (user_id, currency_id, balance)
                values(?,?,?)""";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, userId);
            ps.setLong(2, currencyId);
            ps.setBigDecimal(3, new BigDecimal("0.00"));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Integer updateBalance(Long accountId, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        return jdbcTemplate.update(sql, amount, accountId);
    }

    public Integer countUserAccounts(Long userId) {
        String sql = "select count(*) from users where user_id=?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Integer.class, userId));
    }

    public Optional<Account> getAccountByUserAndId(Long userId, Long accountId) {
        String sql = "select * from accounts where user_id=? and id=?";
        Account account = DataAccessUtils.singleResult(jdbcTemplate.query(sql, new AccountDaoMapper(), userId, accountId));
        return Optional.ofNullable(account);
    }

    public Optional<Account> getAccountById(Long accountId) {
        String sql = "select * from accounts where id=?";
        Account account = DataAccessUtils.singleResult(jdbcTemplate.query(sql, new AccountDaoMapper(), accountId));
        return Optional.ofNullable(account);
    }

    public Optional<Account> getUserAccount(Long userId, Long currencyId) {
        String sql = "select * from accounts where user_id=? and currency_id=?";
        Account account = DataAccessUtils.singleResult(jdbcTemplate.query(sql, new AccountDaoMapper(), userId, currencyId));
        return Optional.ofNullable(account);
    }
}
