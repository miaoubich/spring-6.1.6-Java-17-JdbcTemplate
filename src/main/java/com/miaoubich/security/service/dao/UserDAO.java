package com.miaoubich.security.service.dao;

import com.miaoubich.security.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);

    void saveUser(User user);
}
