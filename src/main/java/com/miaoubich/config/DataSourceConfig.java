package com.miaoubich.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/studentdb");
        ds.setUsername("postgres");
        ds.setPassword("password");
        ds.setMaximumPoolSize(10);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
