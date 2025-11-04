package com.miaoubich.security.service.login.dao;

import com.miaoubich.security.model.User;

import java.util.Optional;

public interface LoginDAO {
    Optional<User> findByUsername(String username);
}
