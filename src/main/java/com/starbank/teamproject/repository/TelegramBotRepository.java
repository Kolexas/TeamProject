package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.User;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class TelegramBotRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public TelegramBotRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate, @Qualifier("recommendationsDataSource") DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public User getUser(String username) throws SQLException {
        String sql = "SELECT first_name, last_name, id FROM users WHERE username = ?";
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                throw new NoSuchElementException("Пользователь не найден!");
            }
            User user = new User();
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setId(UUID.fromString(rs.getString("id")));
            if (rs.next()) {
                throw new IllegalStateException("Найдено несколько пользователей с username" + username);
            }
            return user;
        }
    }
}
