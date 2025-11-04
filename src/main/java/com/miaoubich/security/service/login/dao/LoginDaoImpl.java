package com.miaoubich.security.service.login.dao;

import com.miaoubich.security.model.Role;
import com.miaoubich.security.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginDaoImpl implements LoginDAO {

    private final static Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public LoginDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByUsername(String username) {

        String sql = "SELECT username_, password_, role_ FROM user_ WHERE username_ = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
            User u = new User();
            u.setUsername(rs.getString("username_"));
            u.setPassword(rs.getString("password_"));
            u.setRole(Role.valueOf(rs.getString("role_")));
            return u;
        });
        return (user != null)? Optional.of(user): Optional.empty();
    }
}
