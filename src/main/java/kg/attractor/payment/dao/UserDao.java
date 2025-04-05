package kg.attractor.payment.dao;

import kg.attractor.payment.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public Long register(User user) {
        String sql = """
            insert into users(name, phone_number, email, password, role_id, enabled)
            мф VALUES (?, ?, ?, ?, (select id from roles where role like 'USER'), true)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Boolean existsUserByEmail(String email) {
        String sql = "select count(*) from users where email = ?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, Integer.class, email)) > 0;
    }

}
